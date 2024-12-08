package com.socialmedia.gui;

import com.socialmedia.model.Channel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;

public class SubscriberFrameTest {
    private SubscriberFrame subscriberFrame;
    private Channel channel;

    @BeforeEach
    void setUp() {
        channel = new Channel("Test Channel");
        subscriberFrame = new SubscriberFrame(channel);
    }

    @Test
    void testFrameCreation() {
        assertNotNull(subscriberFrame);
        assertEquals("Channel Subscriber - Test Channel", subscriberFrame.getTitle());
        assertTrue(subscriberFrame.isDisplayable());
    }

    @Test
    void testSubscriptionPanelExists() {
        SubscriptionPanel subscriptionPanel = findSubscriptionPanel(subscriberFrame);
        assertNotNull(subscriptionPanel, "Subscription panel should exist");
    }

    @Test
    void testHeaderLabelExists() {
        JLabel headerLabel = findHeaderLabel(subscriberFrame);
        assertNotNull(headerLabel, "Header label should exist");
        assertEquals("Subscribe to Test Channel", headerLabel.getText());
    }

    // Helper methods to find components
    private SubscriptionPanel findSubscriptionPanel(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof SubscriptionPanel) {
                return (SubscriptionPanel) comp;
            } else if (comp instanceof Container) {
                SubscriptionPanel found = findSubscriptionPanel((Container) comp);
                if (found != null) return found;
            }
        }
        return null;
    }

    private JLabel findHeaderLabel(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().equals("Subscribe to Test Channel")) {
                return (JLabel) comp;
            } else if (comp instanceof Container) {
                JLabel found = findHeaderLabel((Container) comp);
                if (found != null) return found;
            }
        }
        return null;
    }
}
