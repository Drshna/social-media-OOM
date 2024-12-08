package com.socialmedia.gui;

import com.socialmedia.model.Channel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;

public class PublisherFrameTest {
    private PublisherFrame publisherFrame;
    private Channel channel;

    @BeforeEach
    void setUp() {
        channel = new Channel("Test Channel");
        publisherFrame = new PublisherFrame(channel);
    }

    @Test
    void testFrameCreation() {
        assertNotNull(publisherFrame);
        assertEquals("Channel Publisher - Test Channel", publisherFrame.getTitle());
        assertTrue(publisherFrame.isDisplayable());
    }

    @Test
    void testPublisherPanelExists() {
        PublisherPanel publisherPanel = findPublisherPanel(publisherFrame);
        assertNotNull(publisherPanel, "Publisher panel should exist");
    }

    @Test
    void testHeaderLabelExists() {
        JLabel headerLabel = findHeaderLabel(publisherFrame);
        assertNotNull(headerLabel, "Header label should exist");
        assertEquals("Test Channel", headerLabel.getText());
    }

    // Helper methods to find components
    private PublisherPanel findPublisherPanel(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof PublisherPanel) {
                return (PublisherPanel) comp;
            } else if (comp instanceof Container) {
                PublisherPanel found = findPublisherPanel((Container) comp);
                if (found != null) return found;
            }
        }
        return null;
    }

    private JLabel findHeaderLabel(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().equals("Test Channel")) {
                return (JLabel) comp;
            } else if (comp instanceof Container) {
                JLabel found = findHeaderLabel((Container) comp);
                if (found != null) return found;
            }
        }
        return null;
    }
}
