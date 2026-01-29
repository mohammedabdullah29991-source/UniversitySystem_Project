package com.university;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * واجهة بدء بسيطة لنظام إدارة الجامعة المصغر.
 */
public class SimpleSplash extends JFrame {
    
    /**
     * مُنشئ واجهة البدء البسيطة.
     */
    public SimpleSplash() {
        // إعدادات النافذة
        setTitle("نظام إدارة الجامعة المصغر");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // توسيط النافذة
        setLayout(new BorderLayout());
        
        // تعيين أيقونة التطبيق
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/university_logo_named.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            // في حالة عدم العثور على الصورة، تستمر الواجهة بدون أيقونة
        }
        
        // تهيئة المكونات
        initializeComponents();
        
        // عرض النافذة
        setVisible(true);
    }
    
    /**
     * تهيئة مكونات واجهة البدء.
     */
    private void initializeComponents() {
        // لوحة رئيسية
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(41, 128, 185));
        
        // شعار الجامعة
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/resources/university_logo_named.png"));
            Image scaledImage = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(logoLabel);
            mainPanel.add(Box.createVerticalStrut(20));
        } catch (Exception e) {
            // في حالة عدم العثور على الصورة، يتم عرض نص بديل
            JLabel titleLabel = new JLabel("🎓 نظام إدارة الجامعة");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(titleLabel);
            mainPanel.add(Box.createVerticalStrut(20));
        }
        
        // عنوان التطبيق
        JLabel titleLabel = new JLabel("نظام إدارة الجامعة المصغر");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        
        mainPanel.add(Box.createVerticalStrut(10));
        
        // وصف التطبيق
        JLabel descLabel = new JLabel("باستخدام القوائم المرتبطة المفردة");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(Color.WHITE);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(descLabel);
        
        mainPanel.add(Box.createVerticalStrut(30));
        
        // زر البدء
        JButton startButton = new JButton("ابدأ التطبيق");
        startButton.setPreferredSize(new Dimension(180, 40));
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setBackground(new Color(46, 204, 113));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // مستمع الحدث للزر
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // إغلاق واجهة البدء وفتح الواجهة الرئيسية
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new UniversityApp();
                    }
                });
            }
        });
        
        mainPanel.add(startButton);
        
        mainPanel.add(Box.createVerticalStrut(20));
        
        // معلومات المطورين
        JLabel developersLabel = new JLabel("إعداد: محمد ساري، محمد عبدالسلام، اسامة القاسمي");
        developersLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        developersLabel.setForeground(new Color(236, 240, 241));
        developersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(developersLabel);
        
        mainPanel.add(Box.createVerticalStrut(5));
        
        // معلوم الإشراف
        JLabel supervisorLabel = new JLabel("تحت إشراف: د. سعاد الجعيد");
        supervisorLabel.setFont(new Font("Arial", Font.BOLD, 11));
        supervisorLabel.setForeground(new Color(255, 255, 255));
        supervisorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(supervisorLabel);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    /**
     * الدالة الرئيسية لتشغيل واجهة البدء.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleSplash();
            }
        });
    }
}
