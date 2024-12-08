package com.socialmedia.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class UserTest {
    private User user;
    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_CONTENT = "Test content";

    @BeforeEach
    void setUp() {
        user = new User(TEST_USERNAME);
    }

    @Test
    void testUserCreation() {
        assertEquals(TEST_USERNAME, user.getUsername());
        assertTrue(user.getReceivedContent().isEmpty());
    }

    @Test
    void testInvalidUserCreation() {
        assertThrows(IllegalArgumentException.class, () -> new User(null));
        assertThrows(IllegalArgumentException.class, () -> new User(""));
        assertThrows(IllegalArgumentException.class, () -> new User("   "));
    }

    @Test
    void testUsernameIsTrimmed() {
        User userWithSpaces = new User("  " + TEST_USERNAME + "  ");
        assertEquals(TEST_USERNAME, userWithSpaces.getUsername());
    }

    @Test
    void testReceiveContent() {
        user.receiveContent(TEST_CONTENT);
        List<String> content = user.getReceivedContent();
        
        assertFalse(content.isEmpty());
        assertEquals(1, content.size());
        assertEquals(TEST_CONTENT, content.get(0));
    }

    @Test
    void testReceiveInvalidContent() {
        assertThrows(IllegalArgumentException.class, () -> user.receiveContent(null));
        assertThrows(IllegalArgumentException.class, () -> user.receiveContent(""));
        assertThrows(IllegalArgumentException.class, () -> user.receiveContent("   "));
    }

    @Test
    void testContentOrder() {
        String firstContent = "First message";
        String secondContent = "Second message";
        
        user.receiveContent(firstContent);
        user.receiveContent(secondContent);
        
        List<String> content = user.getReceivedContent();
        assertEquals(2, content.size());
        assertEquals(secondContent, content.get(0), "Most recent content should be first");
        assertEquals(firstContent, content.get(1), "Older content should be last");
    }

    @Test
    void testHasReceivedContent() {
        user.receiveContent(TEST_CONTENT);
        assertTrue(user.hasReceivedContent(TEST_CONTENT));
        assertFalse(user.hasReceivedContent("Non-existent content"));
    }

    @Test
    void testHasReceivedContentNull() {
        assertFalse(user.hasReceivedContent(null));
    }

    @Test
    void testGetReceivedContentImmutable() {
        user.receiveContent(TEST_CONTENT);
        List<String> content = user.getReceivedContent();
        
        assertThrows(UnsupportedOperationException.class, 
            () -> content.add("New content"));
    }

    @Test
    void testEqualsAndHashCode() {
        User sameUser = new User(TEST_USERNAME);
        User differentUser = new User("otherUser");
        
        // Test equals
        assertEquals(user, user);  // Same instance
        assertEquals(user, sameUser);  // Same username
        assertNotEquals(user, differentUser);  // Different username
        assertNotEquals(user, null);  // Null comparison
        assertNotEquals(user, new Object());  // Different type
        
        // Test hashCode
        assertEquals(user.hashCode(), sameUser.hashCode());
        assertNotEquals(user.hashCode(), differentUser.hashCode());
    }

    @Test
    void testToString() {
        assertEquals(TEST_USERNAME, user.toString());
    }
}
