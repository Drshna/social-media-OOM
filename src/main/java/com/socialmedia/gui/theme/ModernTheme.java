package com.socialmedia.gui.theme;

import javax.swing.*;
import java.awt.*;

public class ModernTheme {
    // Color scheme
    public static final Color PRIMARY_COLOR = new Color(25, 118, 210); // Material Blue
    public static final Color SECONDARY_COLOR = new Color(66, 165, 245);
    public static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    public static final Color CARD_BACKGROUND = Color.WHITE;
    public static final Color TEXT_COLOR = new Color(33, 33, 33);
    public static final Color SUBTITLE_COLOR = new Color(117, 117, 117);
    
    // Fonts
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    public static void setup() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Configure default fonts
            UIManager.put("Label.font", REGULAR_FONT);
            UIManager.put("Button.font", REGULAR_FONT);
            UIManager.put("TextField.font", REGULAR_FONT);
            UIManager.put("TextArea.font", REGULAR_FONT);
            UIManager.put("List.font", REGULAR_FONT);
            UIManager.put("ComboBox.font", REGULAR_FONT);
            UIManager.put("CheckBox.font", REGULAR_FONT);
            
            // Configure colors
            UIManager.put("Panel.background", BACKGROUND_COLOR);
            UIManager.put("TextField.background", CARD_BACKGROUND);
            UIManager.put("TextArea.background", CARD_BACKGROUND);
            UIManager.put("List.background", CARD_BACKGROUND);
            
            // Button styling
            UIManager.put("Button.background", PRIMARY_COLOR);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.select", SECONDARY_COLOR);
            UIManager.put("Button.focus", new Color(0, 0, 0, 0));
            
            // ToggleButton styling
            UIManager.put("ToggleButton.background", PRIMARY_COLOR);
            UIManager.put("ToggleButton.foreground", Color.WHITE);
            UIManager.put("ToggleButton.select", SECONDARY_COLOR);
            UIManager.put("ToggleButton.focus", new Color(0, 0, 0, 0));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    public static JToggleButton createToggleButton(String text) {
        JToggleButton button = new JToggleButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return panel;
    }
}
