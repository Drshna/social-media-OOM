package com.socialmedia.gui;

import com.socialmedia.model.Channel;
import com.socialmedia.model.User;
import com.socialmedia.gui.theme.ModernTheme;
import javax.swing.*;
import java.awt.*;

public class SubscriptionPanel extends JPanel {
    private final Channel channel;
    private final JTextField usernameField;
    private final JButton subscribeButton;
    private final JPanel contentPanel;
    private SubscriberPanel currentSubscriberPanel;
    private User currentUser;

    public SubscriptionPanel(Channel channel) {
        this.channel = channel;
        
        // Initialize components
        usernameField = new JTextField(20);
        subscribeButton = ModernTheme.createButton("Subscribe");
        contentPanel = new JPanel(new BorderLayout());
        
        // Set up panel
        setLayout(new BorderLayout(20, 20));
        setBackground(ModernTheme.BACKGROUND_COLOR);
        
        // Create subscription controls
        JPanel controlsPanel = createControlsPanel();
        
        // Configure content panel
        contentPanel.setBackground(ModernTheme.BACKGROUND_COLOR);
        
        // Add components
        add(controlsPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createControlsPanel() {
        JPanel panel = ModernTheme.createCardPanel();
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header
        JLabel headerLabel = new JLabel("Subscribe to " + channel.getName());
        headerLabel.setFont(ModernTheme.HEADER_FONT);
        headerLabel.setForeground(ModernTheme.TEXT_COLOR);

        // Username input
        JPanel userPanel = new JPanel(new BorderLayout(10, 5));
        userPanel.setOpaque(false);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(ModernTheme.REGULAR_FONT);
        usernameLabel.setForeground(ModernTheme.TEXT_COLOR);

        usernameField.setFont(ModernTheme.REGULAR_FONT);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        subscribeButton.setEnabled(false);

        userPanel.add(usernameLabel, BorderLayout.WEST);
        userPanel.add(usernameField, BorderLayout.CENTER);
        userPanel.add(subscribeButton, BorderLayout.EAST);

        // Enable/disable subscribe button based on username
        usernameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateButton(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateButton(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateButton(); }
            
            private void updateButton() {
                subscribeButton.setEnabled(!usernameField.getText().trim().isEmpty());
            }
        });

        // Subscribe button action
        subscribeButton.addActionListener(e -> {
            if (currentUser == null) {
                // Subscribe
                subscribe();
            } else {
                // Unsubscribe
                unsubscribe();
            }
        });

        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(userPanel, BorderLayout.CENTER);

        return panel;
    }

    private void subscribe() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) return;

        currentUser = new User(username);
        if (channel.subscribe(currentUser)) {
            // Update UI for subscribed state
            subscribeButton.setText("Unsubscribe");
            usernameField.setEnabled(false);
            
            // Create and show subscriber panel
            if (currentSubscriberPanel != null) {
                currentSubscriberPanel.cleanup();
            }
            currentSubscriberPanel = new SubscriberPanel(channel, currentUser);
            contentPanel.removeAll();
            contentPanel.add(currentSubscriberPanel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
            
            JOptionPane.showMessageDialog(this,
                "Successfully subscribed to " + channel.getName(),
                "Subscription Success",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            currentUser = null;
            JOptionPane.showMessageDialog(this,
                "Failed to subscribe to the channel",
                "Subscription Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void unsubscribe() {
        if (currentUser == null) return;

        channel.unsubscribe(currentUser);
        
        // Cleanup subscriber panel
        if (currentSubscriberPanel != null) {
            currentSubscriberPanel.cleanup();
            currentSubscriberPanel = null;
        }
        
        // Clear content panel
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
        
        // Reset UI state
        currentUser = null;
        subscribeButton.setText("Subscribe");
        usernameField.setEnabled(true);
        usernameField.setText("");
        subscribeButton.setEnabled(false);
        
        JOptionPane.showMessageDialog(this,
            "Successfully unsubscribed from " + channel.getName(),
            "Unsubscription Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
