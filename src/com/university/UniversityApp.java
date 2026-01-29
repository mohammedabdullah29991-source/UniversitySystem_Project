package com.university;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author محمد ساري، محمد عبدالسلام، اسامة القاسمي ، محمد زيد ، علي عبدالله المؤيد 
 * @version 1.0
 * 
 * يمثل هذا الكلاس الواجهة الرسومية (GUI) لنظام إدارة الجامعة المصغر.
 * يستخدم هيكل البيانات StudentList (القائمة المرتبطة المفردة) لإدارة بيانات الطلاب.
 */
public class UniversityApp extends JFrame {

    private StudentList studentList;
    private JTextArea displayArea;
    private JTextField idField, nameField, majorField;
    private JLabel statusLabel;

    /**
     * مُنشئ الواجهة الرسومية.
     */
    public UniversityApp() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        // إعدادات النافذة الرئيسية
        setTitle("نظام إدارة الجامعة المصغر - باستخدام القوائم المرتبطة");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        
        // تعيين أيقونة التطبيق
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/university_logo_named.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            // في حالة عدم العثور على الصورة، تستمر الواجهة بدون أيقونة
        }
        
        // تهيئة قائمة الطلاب
        studentList = new StudentList();
        
        // إضافة بعض البيانات الافتراضية للتجربة
        try {
            studentList.insert(1001, "أحمد علي", "هندسة برمجيات");
            studentList.insert(1002, "فاطمة محمد", "علوم حاسوب");
            studentList.insert(1003, "خالد سعيد", "نظم معلومات");
        } catch (IllegalArgumentException e) {
            // لا شيء، فقط للتأكد من عدم تكرار البيانات
        }

        // تهيئة المكونات
        initializeComponents();
        
        // عرض القائمة عند بدء التشغيل
        updateDisplayArea();
        
        // عرض النافذة
        setLocationRelativeTo(null); // توسيط النافذة
        setVisible(true);
    }

    /**
     * تهيئة جميع مكونات الواجهة الرسومية.
     */
    private void initializeComponents() {
        // 1. لوحة الإدخال (الشمال)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("إدارة بيانات الطلاب"));
        inputPanel.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        inputPanel.setPreferredSize(new Dimension(320, 0));
        
        idField = new JTextField(10);
        nameField = new JTextField(10);
        majorField = new JTextField(10);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("رقم الطالب (ID):"), gbc);

        gbc.gridy = 1;
        formPanel.add(idField, gbc);

        gbc.gridy = 2;
        formPanel.add(new JLabel("اسم الطالب:"), gbc);

        gbc.gridy = 3;
        formPanel.add(nameField, gbc);

        gbc.gridy = 4;
        formPanel.add(new JLabel("التخصص:"), gbc);

        gbc.gridy = 5;
        formPanel.add(majorField, gbc);

        // أزرار العمليات
        JPanel buttonsPanel = new JPanel(new GridLayout(5, 1, 8, 8));
        buttonsPanel.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JButton addButton = new JButton("إضافة طالب (Insert)");
        addButton.addActionListener(new AddStudentListener());
        buttonsPanel.add(addButton);

        JButton searchButton = new JButton("بحث عن طالب (Search)");
        searchButton.addActionListener(new SearchStudentListener());
        buttonsPanel.add(searchButton);

        JButton updateButton = new JButton("تعديل التخصص (Update)");
        updateButton.addActionListener(new UpdateMajorListener());
        buttonsPanel.add(updateButton);

        JButton deleteButton = new JButton("حذف طالب (Delete)");
        deleteButton.addActionListener(new DeleteStudentListener());
        buttonsPanel.add(deleteButton);

        JButton displayButton = new JButton("عرض القائمة (Display)");
        displayButton.addActionListener(new DisplayStudentsListener());
        buttonsPanel.add(displayButton);

        inputPanel.add(formPanel, BorderLayout.NORTH);
        inputPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        // 2. لوحة العرض الرئيسية (الوسط)
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("قائمة الطلاب المسجلين (Singly Linked List)"));
        
        // 3. لوحة معلومات الطلاب (الجنوب)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("إعداد الطلاب"));
        infoPanel.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        
        // إضافة شعار الجامعة
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/resources/university_logo_named.png"));
            Image scaledImage = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            infoPanel.add(logoLabel);
        } catch (Exception e) {
            // في حالة عدم العثور على الصورة، تستمر الواجهة بدون شعار
        }
        
        infoPanel.add(new JLabel("محمد زيد"));
        infoPanel.add(new JLabel("محمد ساري"));
        infoPanel.add(new JLabel("اسامة القاسمي"));
        infoPanel.add(new JLabel("علي المؤيد"));
        infoPanel.add(new JLabel("محمد عبدالسلام"));
        infoPanel.add(new JLabel("تحت إشراف: د. سعاد الجعيد"));

        statusLabel = new JLabel("الحالة: جاهز");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        southPanel.add(infoPanel, BorderLayout.CENTER);
        southPanel.add(statusLabel, BorderLayout.SOUTH);

        // إضافة اللوحات إلى النافذة الرئيسية
        add(inputPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * تحديث منطقة عرض القائمة.
     */
    private void updateDisplayArea() {
        displayArea.setText(studentList.display());
        displayArea.setCaretPosition(0);
    }

    // -----------------------------------------------------------------
    // فئات المستمعين (Action Listeners)
    // -----------------------------------------------------------------

    /**
     * مستمع لزر إضافة طالب (Insert).
     */
    private class AddStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String major = majorField.getText().trim();

                if (name.isEmpty() || major.isEmpty()) {
                    JOptionPane.showMessageDialog(UniversityApp.this, "الرجاء إدخال جميع البيانات (الاسم والتخصص).", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                studentList.insert(id, name, major);
                JOptionPane.showMessageDialog(UniversityApp.this, "تم إضافة الطالب بنجاح.", "نجاح", JOptionPane.INFORMATION_MESSAGE);
                updateDisplayArea();
                statusLabel.setText("الحالة: تم إضافة الطالب " + id);
                // مسح الحقول
                idField.setText("");
                nameField.setText("");
                majorField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(UniversityApp.this, "رقم الطالب يجب أن يكون رقماً صحيحاً.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
                statusLabel.setText("الحالة: خطأ في إدخال رقم الطالب");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(UniversityApp.this, ex.getMessage(), "خطأ في الإضافة", JOptionPane.ERROR_MESSAGE);
                statusLabel.setText("الحالة: " + ex.getMessage());
            }
        }
    }

    /**
     * مستمع لزر البحث عن طالب (Search).
     */
    private class SearchStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                if (id <= 0) {
                    JOptionPane.showMessageDialog(UniversityApp.this, "رقم الطالب يجب أن يكون رقماً موجباً.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Student student = studentList.search(id);

                if (student != null) {
                    nameField.setText(student.name);
                    majorField.setText(student.major);
                    JOptionPane.showMessageDialog(UniversityApp.this, 
                        "تم العثور على الطالب:\n" + student.toString(), 
                        "نتيجة البحث", 
                        JOptionPane.INFORMATION_MESSAGE);
                    statusLabel.setText("الحالة: تم العثور على الطالب " + id);
                } else {
                    JOptionPane.showMessageDialog(UniversityApp.this, 
                        "لم يتم العثور على طالب برقم " + id, 
                        "نتيجة البحث", 
                        JOptionPane.WARNING_MESSAGE);
                    statusLabel.setText("الحالة: لم يتم العثور على الطالب " + id);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(UniversityApp.this, "الرجاء إدخال رقم الطالب للبحث.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
                statusLabel.setText("الحالة: الرجاء إدخال رقم للبحث");
            }
        }
    }
    
    /**
     * مستمع لزر تعديل التخصص (Update).
     */
    private class UpdateMajorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                if (id <= 0) {
                    JOptionPane.showMessageDialog(UniversityApp.this, "رقم الطالب يجب أن يكون رقماً موجباً.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String newMajor = majorField.getText().trim();

                if (newMajor.isEmpty()) {
                    JOptionPane.showMessageDialog(UniversityApp.this, "الرجاء إدخال التخصص الجديد.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = studentList.updateMajor(id, newMajor);

                if (success) {
                    JOptionPane.showMessageDialog(UniversityApp.this, "تم تعديل تخصص الطالب بنجاح.", "نجاح", JOptionPane.INFORMATION_MESSAGE);
                    updateDisplayArea();
                    majorField.setText("");
                    statusLabel.setText("الحالة: تم تعديل تخصص الطالب " + id);
                } else {
                    JOptionPane.showMessageDialog(UniversityApp.this, "لم يتم العثور على طالب برقم " + id + " لتعديل بياناته.", "خطأ في التعديل", JOptionPane.ERROR_MESSAGE);
                    statusLabel.setText("الحالة: لم يتم العثور على الطالب " + id);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(UniversityApp.this, "الرجاء إدخال رقم الطالب والتخصص الجديد.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
                statusLabel.setText("الحالة: خطأ في الإدخال");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(UniversityApp.this, ex.getMessage(), "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
                statusLabel.setText("الحالة: " + ex.getMessage());
            }
        }
    }

    private class DisplayStudentsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateDisplayArea();
            statusLabel.setText("الحالة: تم تحديث عرض القائمة");
        }
    }

    /**
     * مستمع لزر حذف طالب (Delete).
     */
    private class DeleteStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                if (id <= 0) {
                    JOptionPane.showMessageDialog(UniversityApp.this, "رقم الطالب يجب أن يكون رقماً موجباً.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int choice = JOptionPane.showConfirmDialog(
                    UniversityApp.this,
                    "هل أنت متأكد من حذف الطالب برقم " + id + "؟",
                    "تأكيد الحذف",
                    JOptionPane.YES_NO_OPTION
                );
                if (choice != JOptionPane.YES_OPTION) {
                    statusLabel.setText("الحالة: تم إلغاء الحذف");
                    return;
                }
                
                boolean success = studentList.delete(id);

                if (success) {
                    JOptionPane.showMessageDialog(UniversityApp.this, "تم حذف الطالب برقم " + id + " بنجاح.", "نجاح", JOptionPane.INFORMATION_MESSAGE);
                    updateDisplayArea();
                    idField.setText("");
                    nameField.setText("");
                    majorField.setText("");
                    statusLabel.setText("الحالة: تم حذف الطالب " + id);
                } else {
                    JOptionPane.showMessageDialog(UniversityApp.this, "فشل الحذف: لم يتم العثور على طالب برقم " + id + " أو القائمة فارغة.", "خطأ في الحذف", JOptionPane.ERROR_MESSAGE);
                    statusLabel.setText("الحالة: فشل الحذف للطالب " + id);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(UniversityApp.this, "الرجاء إدخال رقم الطالب للحذف.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
                statusLabel.setText("الحالة: الرجاء إدخال رقم للحذف");
            }
        }
    }

    /**
     * الدالة الرئيسية لتشغيل التطبيق.
     */
    public static void main(String[] args) {
        // التأكد من تشغيل واجهة البدء أولاً
        Main.launch();
    }
}
