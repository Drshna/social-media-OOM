package com.socialmedia;

import com.socialmedia.gui.PublisherFrame;
import com.socialmedia.gui.SubscriberFrame;
import com.socialmedia.model.Channel;

import javax.swing.*;

public class SocialMediaApp {
    public static void main(String[] args) {
        // Create shared channel
        Channel mainChannel = new Channel("Main Channel");
        
        // Create and show both frames using SwingUtilities
        SwingUtilities.invokeLater(() -> {
            // Create and show publisher window
            PublisherFrame publisherFrame = new PublisherFrame(mainChannel);
            publisherFrame.setLocation(100, 100);
            publisherFrame.setVisible(true);
            
            // Create and show subscriber window
            SubscriberFrame subscriberFrame = new SubscriberFrame(mainChannel);
            subscriberFrame.setLocation(publisherFrame.getX() + publisherFrame.getWidth() + 20, publisherFrame.getY());
            subscriberFrame.setVisible(true);
        });
    }
}
