package com.socialmedia.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChannelTest {
    private Channel channel;
    private User user;
    private static final String CHANNEL_NAME = "Test Channel";
    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_CONTENT = "Test message";

    @BeforeEach
    void setUp() {
        channel = new Channel(CHANNEL_NAME);
        user = new User(TEST_USERNAME);
    }

    @Test
    void testChannelCreation() {
        assertEquals(CHANNEL_NAME, channel.getName());
        assertTrue(channel.getSubscribers().isEmpty());
        assertTrue(channel.getPosts().isEmpty());
    }

    @Test
    void testInvalidChannelCreation() {
        assertThrows(IllegalArgumentException.class, () -> new Channel(null));
        assertThrows(IllegalArgumentException.class, () -> new Channel(""));
        assertThrows(IllegalArgumentException.class, () -> new Channel("   "));
    }

    @Test
    void testSubscribe() {
        assertTrue(channel.subscribe(user));
        assertTrue(channel.getSubscribers().contains(user));
        assertEquals(1, channel.getSubscribers().size());
    }

    @Test
    void testSubscribeNull() {
        assertThrows(IllegalArgumentException.class, () -> channel.subscribe(null));
    }

    @Test
    void testSubscribeDuplicate() {
        assertTrue(channel.subscribe(user));
        assertFalse(channel.subscribe(user), "Should not be able to subscribe the same user twice");
        assertEquals(1, channel.getSubscribers().size());
    }

    @Test
    void testUnsubscribe() {
        channel.subscribe(user);
        assertTrue(channel.unsubscribe(user));
        assertFalse(channel.getSubscribers().contains(user));
        assertEquals(0, channel.getSubscribers().size());
    }

    @Test
    void testUnsubscribeNull() {
        assertThrows(IllegalArgumentException.class, () -> channel.unsubscribe(null));
    }

    @Test
    void testUnsubscribeNonSubscribed() {
        assertFalse(channel.unsubscribe(user), "Should return false when unsubscribing a non-subscribed user");
    }

    @Test
    void testPostContent() {
        channel.subscribe(user);
        channel.postContent(TEST_CONTENT);
        
        assertEquals(1, channel.getPosts().size());
        assertTrue(user.hasReceivedContent(channel.getPosts().get(0).toString()));
    }

    @Test
    void testPostContentValidation() {
        assertThrows(IllegalArgumentException.class, () -> channel.postContent(null));
        assertThrows(IllegalArgumentException.class, () -> channel.postContent(""));
        assertThrows(IllegalArgumentException.class, () -> channel.postContent("   "));
        assertThrows(IllegalArgumentException.class, 
            () -> channel.postContent("a".repeat(channel.getMaxContentLength() + 1)));
    }

    @Test
    void testAddPost() {
        Post post = new Post(TEST_CONTENT);
        channel.addPost(post);
        
        assertTrue(channel.getPosts().contains(post));
        assertEquals(1, channel.getPosts().size());
    }

    @Test
    void testAddPostNull() {
        assertThrows(IllegalArgumentException.class, () -> channel.addPost(null));
    }

    @Test
    void testGetSubscribersImmutable() {
        channel.subscribe(user);
        assertThrows(UnsupportedOperationException.class, 
            () -> channel.getSubscribers().add(new User("another")));
    }

    @Test
    void testGetPostsImmutable() {
        channel.addPost(new Post(TEST_CONTENT));
        assertThrows(UnsupportedOperationException.class, 
            () -> channel.getPosts().add(new Post("another")));
    }

    @Test
    void testMaxContentLength() {
        assertTrue(channel.getMaxContentLength() > 0);
        assertEquals(280, channel.getMaxContentLength());
    }

    @Test
    void testPostContentToMultipleSubscribers() {
        User user2 = new User("testUser2");
        channel.subscribe(user);
        channel.subscribe(user2);
        
        channel.postContent(TEST_CONTENT);
        
        assertTrue(user.hasReceivedContent(channel.getPosts().get(0).toString()));
        assertTrue(user2.hasReceivedContent(channel.getPosts().get(0).toString()));
    }
}
