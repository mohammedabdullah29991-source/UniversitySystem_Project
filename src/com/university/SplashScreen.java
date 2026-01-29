package com.university;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author محمد ساري، محمد عبدالسلام، اسامة القاسمي
 * @version 1.0
 * 
 * واجهة البدء (Splash Screen) لنظام إدارة الجامعة المصغر.
 * تعرض شعار الجامعة وزر للدخول إلى الواجهة الرئيسية.
 */
public class SplashScreen extends JFrame {
    
    private Timer timer;
    private int alpha = 0;
    private boolean fadeIn = true;
    
    /**
     * مُنشئ واجهة البدء.
     */
    public SplashScreen() {
        // إعدادات النافذة
        setTitle("نظام إدارة الجامعة المصغر");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setUndecorated(true); // إزالة إطار النافذة
        setLocationRelativeTo(null); // توسيط النافذة
        
        // تعيين أيقونة التطبيق
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/university_logo_named.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            // في حالة عدم العثور على الصورة، تستمر الواجهة بدون أيقونة
        }
        
        // تهيئة المكونات
        initializeComponents();
        
        // بدء تأثير التلاشي
        startFadeEffect();
        
        // عرض النافذة
        setVisible(true);
    }
    
    /**
     * تهيئة مكونات واجهة البدء.
     */
    private void initializeComponents() {
        // لوحة رئيسية مع خلفية شبه شفافة
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // رسم خلفية متدرجة
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(41, 128, 185), 
                                                           0, getHeight(), new Color(52, 152, 219));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        
        // لوحة المحتوى
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // شعار الجامعة
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/resources/university_logo_named.png"));
            Image scaledImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(logoLabel);
            contentPanel.add(Box.createVerticalStrut(20));
        } catch (Exception e) {
            // في حالة عدم العثور على الصورة، يتم عرض نص بديل
            JLabel titleLabel = new JLabel("🎓 نظام إدارة الجامعة");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(titleLabel);
            contentPanel.add(Box.createVerticalStrut(20));
        }
        
        // عنوان التطبيق
        JLabel titleLabel = new JLabel("نظام إدارة الجامعة المصغر");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        
        contentPanel.add(Box.createVerticalStrut(10));
        
        // وصف التطبيق
        JLabel descLabel = new JLabel("باستخدام القوائم المرتبطة المفردة");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        descLabel.setForeground(new Color(236, 240, 241));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(descLabel);
        
        contentPanel.add(Box.createVerticalStrut(30));
        
        // زر البدء
        JButton startButton = createStartButton();
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(startButton);
        
        contentPanel.add(Box.createVerticalStrut(20));
        
        // معلومات المطورين
        JLabel developersLabel = new JLabel("إعداد: محمد ساري، محمد عبدالسلام، اسامة القاسمي");
        developersLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        developersLabel.setForeground(new Color(189, 195, 199));
        developersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(developersLabel);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    /**
     * إنشاء زر البدء المخصص.
     */
    private JButton createStartButton() {
        JButton startButton = new JButton("ابدأ التطبيق") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // رسم خلفية مستديرة
                g2d.setColor(new Color(46, 204, 113));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                
                // رسم النص
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(getText(), x, y);
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                // بدون حدود
            }
            
            @Override
            public boolean isContentAreaFilled() {
                return false;
            }
        };
        
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.setFocusPainted(false);
        
        // تأثيرات الماوس
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(39, 174, 96));
                startButton.repaint();
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(46, 204, 113));
                startButton.repaint();
            }
        });
        
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
        
        return startButton;
    }
    
    /**
     * بدء تأثير التلاشي التدريجي.
     */
    private void startFadeEffect() {
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fadeIn) {
                    alpha += 5;
                    if (alpha >= 255) {
                        alpha = 255;
                        fadeIn = false;
                        timer.stop();
                    }
                }
                setOpacity(alpha / 255.0f);
            }
        });
        timer.start();
    }
    
    /**
     * الدالة الرئيسية لتشغيل واجهة البدء.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SplashScreen();
            }
        });
    }
}
