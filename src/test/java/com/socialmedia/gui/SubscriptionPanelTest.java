package com.socialmedia.gui;

import com.socialmedia.model.Channel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;

public class SubscriptionPanelTest {
    private SubscriptionPanel subscriptionPanel;
    private Channel channel;

    @BeforeEach
    void setUp() {
        channel = new Channel("Test Channel");
        subscriptionPanel = new SubscriptionPanel(channel);
    }

    @Test
    void testPanelCreation() {
        assertNotNull(subscriptionPanel);
        assertTrue(subscriptionPanel.isDisplayable());
    }

    @Test
    void testUsernameFieldExists() {
        JTextField usernameField = findUsernameField(subscriptionPanel);
        assertNotNull(usernameField, "Username field should exist");
        assertTrue(usernameField.isEditable(), "Username field should be editable");
    }

    @Test
    void testSubscribeButtonExists() {
        JButton subscribeButton = findSubscribeButton(subscriptionPanel);
        assertNotNull(subscribeButton, "Subscribe button should exist");
        assertEquals("Subscribe", subscribeButton.getText());
    }

    @Test
    void testContentPanelExists() {
        JPanel contentPanel = findContentPanel(subscriptionPanel);
        assertNotNull(contentPanel, "Content panel should exist");
    }

    // Helper methods to find components
    private JTextField findUsernameField(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JTextField) {
                return (JTextField) comp;
            } else if (comp instanceof Container) {
                JTextField found = findUsernameField((Container) comp);
                if (found != null)
                    return found;
            }
        }
        return null;
    }

    private JButton findSubscribeButton(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton &&
                    (((JButton) comp).getText().equals("Subscribe") ||
                            ((JButton) comp).getText().equals("Unsubscribe"))) {
                return (JButton) comp;
            } else if (comp instanceof Container) {
                JButton found = findSubscribeButton((Container) comp);
                if (found != null)
                    return found;
            }
        }
        return null;
    }

    private JPanel findContentPanel(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JPanel && comp.getParent() instanceof SubscriptionPanel) {
                return (JPanel) comp;
            } else if (comp instanceof Container) {
                JPanel found = findContentPanel((Container) comp);
                if (found != null)
                    return found;
            }
        }
        return null;
    }
}
