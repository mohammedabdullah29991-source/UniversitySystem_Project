package com.university;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author محمد ساري، محمد عبدالسلام، اسامة القاسمي
 * @version 1.0
 * 
 * يمثل هذا الكلاس الواجهة الرسومية (GUI) لنظام إدارة الجامعة المصغر.
 * يستخدم هيكل البيانات StudentList (القائمة المرتبطة المفردة) لإدارة بيانات الطلاب.
 */
public class UniversityApp extends JFrame {

    private StudentList studentList;
    private JTextArea displayArea;
    private JTextField idField, nameField, majorField;

    /**
     * مُنشئ الواجهة الرسومية.
     */
    public UniversityApp() {
        // إعدادات النافذة الرئيسية
        setTitle("نظام إدارة الجامعة المصغر - باستخدام القوائم المرتبطة");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
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
        inputPanel.setLayout(new GridLayout(10, 1, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("إدارة بيانات الطلاب"));
        
        idField = new JTextField(10);
        nameField = new JTextField(10);
        majorField = new JTextField(10);

        inputPanel.add(new JLabel("رقم الطالب (ID):"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("اسم الطالب:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("التخصص:"));
        inputPanel.add(majorField);

        // أزرار العمليات
        JButton addButton = new JButton("إضافة طالب (Insert)");
        addButton.addActionListener(new AddStudentListener());
        inputPanel.add(addButton);
        
        JButton searchButton = new JButton("بحث عن طالب (Search)");
        searchButton.addActionListener(new SearchStudentListener());
        inputPanel.add(searchButton);
        
        JButton updateButton = new JButton("تعديل التخصص (Update)");
        updateButton.addActionListener(new UpdateMajorListener());
        inputPanel.add(updateButton);
        
        JButton deleteButton = new JButton("حذف طالب (Delete)");
        deleteButton.addActionListener(new DeleteStudentListener());
        inputPanel.add(deleteButton);
        
        // 2. لوحة العرض الرئيسية (الوسط)
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("قائمة الطلاب المسجلين (Singly Linked List)"));
        
        // 3. لوحة معلومات الطلاب (الجنوب)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("إعداد الطلاب"));
        
        // إضافة شعار الجامعة
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/resources/university_logo_named.png"));
            Image scaledImage = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            infoPanel.add(logoLabel);
        } catch (Exception e) {
            // في حالة عدم العثور على الصورة، تستمر الواجهة بدون شعار
        }
        
        infoPanel.add(new JLabel("محمد ساري"));
        infoPanel.add(new JLabel("محمد عبدالسلام"));
        infoPanel.add(new JLabel("اسامة القاسمي"));
        infoPanel.add(new JLabel("تحت إشراف: د. سعاد الجعيد"));

        // إضافة اللوحات إلى النافذة الرئيسية
        add(inputPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }

    /**
     * تحديث منطقة عرض القائمة.
     */
    private void updateDisplayArea() {
        displayArea.setText(studentList.display());
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
                // مسح الحقول
                idField.setText("");
                nameField.setText("");
                majorField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(UniversityApp.this, "رقم الطالب يجب أن يكون رقماً صحيحاً.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(UniversityApp.this, ex.getMessage(), "خطأ في الإضافة", JOptionPane.ERROR_MESSAGE);
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
                Student student = studentList.search(id);

                if (student != null) {
                    JOptionPane.showMessageDialog(UniversityApp.this, 
                        "تم العثور على الطالب:\n" + student.toString(), 
                        "نتيجة البحث", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(UniversityApp.this, 
                        "لم يتم العثور على طالب برقم " + id, 
                        "نتيجة البحث", 
                        JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(UniversityApp.this, "الرجاء إدخال رقم الطالب للبحث.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
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
                String newMajor = majorField.getText().trim();

                if (newMajor.isEmpty()) {
                    JOptionPane.showMessageDialog(UniversityApp.this, "الرجاء إدخال التخصص الجديد.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = studentList.updateMajor(id, newMajor);

                if (success) {
                    JOptionPane.showMessageDialog(UniversityApp.this, "تم تعديل تخصص الطالب بنجاح.", "نجاح", JOptionPane.INFORMATION_MESSAGE);
                    updateDisplayArea();
                } else {
                    JOptionPane.showMessageDialog(UniversityApp.this, "لم يتم العثور على طالب برقم " + id + " لتعديل بياناته.", "خطأ في التعديل", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(UniversityApp.this, "الرجاء إدخال رقم الطالب والتخصص الجديد.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
            }
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
                
                boolean success = studentList.delete(id);

                if (success) {
                    JOptionPane.showMessageDialog(UniversityApp.this, "تم حذف الطالب برقم " + id + " بنجاح.", "نجاح", JOptionPane.INFORMATION_MESSAGE);
                    updateDisplayArea();
                } else {
                    JOptionPane.showMessageDialog(UniversityApp.this, "فشل الحذف: لم يتم العثور على طالب برقم " + id + " أو القائمة فارغة.", "خطأ في الحذف", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(UniversityApp.this, "الرجاء إدخال رقم الطالب للحذف.", "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * الدالة الرئيسية لتشغيل التطبيق.
     */
    public static void main(String[] args) {
        // التأكد من تشغيل واجهة البدء أولاً
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleSplash();
            }
        });
    }
}
