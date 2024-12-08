package com.socialmedia.gui;

import com.socialmedia.model.Channel;
import com.socialmedia.gui.theme.ModernTheme;
import javax.swing.*;
import java.awt.*;

public class PublisherFrame extends JFrame {
    private final Channel channel;
    private final PublisherPanel publisherPanel;

    public PublisherFrame(Channel channel) {
        this.channel = channel;
        
        // Set up frame
        setTitle("Channel Publisher - " + channel.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300));
        
        // Apply modern theme
        ModernTheme.setup();
        
        // Create publisher panel
        publisherPanel = new PublisherPanel(channel);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(ModernTheme.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add header
        JLabel headerLabel = new JLabel(channel.getName());
        headerLabel.setFont(ModernTheme.HEADER_FONT);
        headerLabel.setForeground(ModernTheme.TEXT_COLOR);
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Add publisher panel
        mainPanel.add(publisherPanel, BorderLayout.CENTER);
        
        // Set content pane
        setContentPane(mainPanel);
        
        // Pack and center
        pack();
        setLocationRelativeTo(null);
    }
}
