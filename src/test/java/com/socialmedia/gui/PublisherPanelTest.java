package com.socialmedia.gui;

import com.socialmedia.model.Channel;
import com.socialmedia.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;

public class PublisherPanelTest {
    private PublisherPanel publisherPanel;
    private Channel channel;

    @BeforeEach
    void setUp() {
        channel = new Channel("Test Channel");
        publisherPanel = new PublisherPanel(channel);
    }

    @Test
    void testPanelCreation() {
        assertNotNull(publisherPanel);
        assertTrue(publisherPanel.isDisplayable());
    }

    @Test
    void testTextAreaExists() {
        JTextArea textArea = findTextArea(publisherPanel);
        assertNotNull(textArea, "Text area should exist");
        assertTrue(textArea.isEditable(), "Text area should be editable");
    }

    @Test
    void testPublishButtonExists() {
        JButton publishButton = findPublishButton(publisherPanel);
        assertNotNull(publishButton, "Publish button should exist");
        assertEquals("Publish", publishButton.getText());
    }

    @Test
    void testCharacterCountLabel() {
        JLabel countLabel = findCharacterCountLabel(publisherPanel);
        assertNotNull(countLabel, "Character count label should exist");
        assertTrue(countLabel.getText().contains("280"), "Should show max character count");
    }

    // Helper methods to find components
    private JTextArea findTextArea(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JTextArea) {
                return (JTextArea) comp;
            } else if (comp instanceof Container) {
                JTextArea found = findTextArea((Container) comp);
                if (found != null) return found;
            }
        }
        return null;
    }

    private JButton findPublishButton(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton && ((JButton) comp).getText().equals("Publish")) {
                return (JButton) comp;
            } else if (comp instanceof Container) {
                JButton found = findPublishButton((Container) comp);
                if (found != null) return found;
            }
        }
        return null;
    }

    private JLabel findCharacterCountLabel(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().contains("280")) {
                return (JLabel) comp;
            } else if (comp instanceof Container) {
                JLabel found = findCharacterCountLabel((Container) comp);
                if (found != null) return found;
            }
        }
        return null;
    }
}
