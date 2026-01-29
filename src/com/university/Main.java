package com.university;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void launch() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                new SimpleSplash();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
