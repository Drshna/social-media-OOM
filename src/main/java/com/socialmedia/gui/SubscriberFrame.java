package com.socialmedia.gui;

import com.socialmedia.model.Channel;
import com.socialmedia.gui.theme.ModernTheme;
import javax.swing.*;
import java.awt.*;

public class SubscriberFrame extends JFrame {
    private final Channel channel;
    private final SubscriptionPanel subscriptionPanel;

    public SubscriberFrame(Channel channel) {
        this.channel = channel;
        
        // Set up frame
        setTitle("Channel Subscriber - " + channel.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 600));
        
        // Apply modern theme
        ModernTheme.setup();
        
        // Create subscription panel
        subscriptionPanel = new SubscriptionPanel(channel);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(ModernTheme.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add header
        JLabel headerLabel = new JLabel("Subscribe to " + channel.getName());
        headerLabel.setFont(ModernTheme.HEADER_FONT);
        headerLabel.setForeground(ModernTheme.TEXT_COLOR);
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Add subscription panel
        mainPanel.add(subscriptionPanel, BorderLayout.CENTER);
        
        // Set content pane
        setContentPane(mainPanel);
        
        // Pack and center
        pack();
        setLocationRelativeTo(null);
    }
}
