package com.socialmedia.gui;

import com.socialmedia.model.Channel;
import com.socialmedia.model.User;
import com.socialmedia.model.Post;
import com.socialmedia.gui.theme.ModernTheme;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class SubscriberPanel extends JPanel {
    private final DefaultListModel<String> contentListModel;
    private final User user;
    private final Channel channel;
    private Timer refreshTimer;
    private static final int REFRESH_INTERVAL = 1000; // 1 second

    public SubscriberPanel(Channel channel, User user) {
        if (channel == null || user == null) {
            throw new IllegalArgumentException("Channel and user cannot be null");
        }
        
        this.channel = channel;
        this.user = user;
        setLayout(new BorderLayout(10, 10));
        setBackground(ModernTheme.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create header with title and controls
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Content Feed");
        titleLabel.setFont(ModernTheme.HEADER_FONT);
        titleLabel.setForeground(ModernTheme.TEXT_COLOR);

        JLabel usernameLabel = new JLabel("@" + user.getUsername());
        usernameLabel.setFont(ModernTheme.REGULAR_FONT);
        usernameLabel.setForeground(ModernTheme.SUBTITLE_COLOR);

        JPanel titlePanel = new JPanel(new BorderLayout(5, 5));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(usernameLabel, BorderLayout.CENTER);

        // Create content list
        contentListModel = new DefaultListModel<>();
        JList<String> contentList = new JList<>(contentListModel);
        contentList.setCellRenderer(new ContentCellRenderer());
        contentList.setBackground(ModernTheme.CARD_BACKGROUND);
        contentList.setSelectionBackground(new Color(ModernTheme.PRIMARY_COLOR.getRGB() & 0x20FFFFFF, true));
        contentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(contentList);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1));
        scrollPane.setBackground(ModernTheme.CARD_BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Create refresh controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        controlPanel.setOpaque(false);

        JCheckBox autoRefreshCheckbox = new JCheckBox("Auto Refresh", true);
        autoRefreshCheckbox.setFont(ModernTheme.REGULAR_FONT);
        autoRefreshCheckbox.setForeground(ModernTheme.TEXT_COLOR);
        autoRefreshCheckbox.setOpaque(false);
        autoRefreshCheckbox.setFocusPainted(false);

        JButton manualRefreshButton = ModernTheme.createButton("Refresh Now");
        manualRefreshButton.setFocusPainted(false);

        controlPanel.add(autoRefreshCheckbox);
        controlPanel.add(manualRefreshButton);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(controlPanel, BorderLayout.EAST);

        // Setup auto-refresh
        refreshTimer = new Timer(REFRESH_INTERVAL, e -> refreshContent());
        refreshTimer.setInitialDelay(0);
        refreshTimer.start();

        autoRefreshCheckbox.addActionListener(e -> {
            if (autoRefreshCheckbox.isSelected()) {
                refreshTimer.restart();
            } else {
                refreshTimer.stop();
            }
        });

        manualRefreshButton.addActionListener(e -> {
            refreshContent();
            if (!autoRefreshCheckbox.isSelected()) {
                // Visual feedback for manual refresh
                manualRefreshButton.setEnabled(false);
                Timer enableTimer = new Timer(500, evt -> manualRefreshButton.setEnabled(true));
                enableTimer.setRepeats(false);
                enableTimer.start();
            }
        });

        // Layout components
        JPanel mainPanel = ModernTheme.createCardPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Initial content load
        refreshContent();
    }

    public void refreshContent() {
        SwingUtilities.invokeLater(() -> {
            try {
                contentListModel.clear();
                java.util.List<String> content = user.getReceivedContent();
                if (content.isEmpty()) {
                    contentListModel.addElement("No content yet. Subscribe to channels to see their posts!");
                } else {
                    for (String message : content) {
                        contentListModel.addElement(message);
                    }
                }
            } catch (Exception e) {
                contentListModel.clear();
                contentListModel.addElement("Error refreshing content: " + e.getMessage());
            }
        });
    }

    public void cleanup() {
        if (refreshTimer != null && refreshTimer.isRunning()) {
            refreshTimer.stop();
        }
    }

    private static class ContentCellRenderer extends DefaultListCellRenderer {
        private static final int MAX_WIDTH = 600;
        
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            
            label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            label.setFont(ModernTheme.REGULAR_FONT);
            
            if (!isSelected) {
                label.setForeground(ModernTheme.TEXT_COLOR);
                label.setBackground(ModernTheme.CARD_BACKGROUND);
            }
            
            // Enable text wrapping
            String text = label.getText();
            if (text != null) {
                FontMetrics fm = label.getFontMetrics(label.getFont());
                int width = Math.min(fm.stringWidth(text) + 30, MAX_WIDTH);
                label.setPreferredSize(new Dimension(width, 
                    fm.getHeight() + 20 + (int)(text.length() / (MAX_WIDTH / fm.getHeight()))));
            }
            
            return label;
        }
    }
}
