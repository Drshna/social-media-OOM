package com.socialmedia.gui;

import com.socialmedia.model.Channel;
import com.socialmedia.gui.theme.ModernTheme;
import javax.swing.*;
import java.awt.*;

public class PublisherPanel extends JPanel {
    private final JTextArea contentArea;
    private final JLabel charCountLabel;
    private JButton postButton;
    private Channel channel;
    private static final int MAX_CHARS = 280;

    public PublisherPanel(Channel channel) {
        this.channel = channel;
        setLayout(new BorderLayout(10, 10));
        setBackground(ModernTheme.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create header
        JLabel headerLabel = new JLabel("Create Post");
        headerLabel.setFont(ModernTheme.HEADER_FONT);
        headerLabel.setForeground(ModernTheme.TEXT_COLOR);

        // Create content input area
        JPanel cardPanel = ModernTheme.createCardPanel();
        cardPanel.setLayout(new BorderLayout(5, 5));

        contentArea = new JTextArea(5, 30);
        contentArea.setFont(ModernTheme.REGULAR_FONT);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(null);

        // Create character counter
        charCountLabel = new JLabel(MAX_CHARS + " characters remaining");
        charCountLabel.setFont(ModernTheme.SMALL_FONT);
        charCountLabel.setForeground(ModernTheme.SUBTITLE_COLOR);

        contentArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateCharCount(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateCharCount(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateCharCount(); }
        });

        // Create post button
        postButton = ModernTheme.createButton("Post");
        postButton.addActionListener(e -> postContent());

        // Layout components
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(charCountLabel, BorderLayout.WEST);
        bottomPanel.add(postButton, BorderLayout.EAST);

        cardPanel.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(headerLabel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
        contentArea.setText("");
        contentArea.setEnabled(channel != null);
        postButton.setEnabled(channel != null);
    }

    private void updateCharCount() {
        int remaining = MAX_CHARS - contentArea.getText().length();
        charCountLabel.setText(remaining + " characters remaining");
        charCountLabel.setForeground(remaining < 20 ? Color.RED : ModernTheme.SUBTITLE_COLOR);
    }

    private void postContent() {
        String content = contentArea.getText().trim();
        if (content.isEmpty()) {
            showError("Please enter some content to post.");
            return;
        }
        if (content.length() > MAX_CHARS) {
            showError("Content exceeds maximum length of " + MAX_CHARS + " characters.");
            return;
        }
        try {
            channel.postContent(content);
            contentArea.setText("");
            showSuccess("Content posted successfully!");
        } catch (Exception e) {
            showError("Error posting content: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
