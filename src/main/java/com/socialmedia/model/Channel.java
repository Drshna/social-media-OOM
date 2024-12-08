package com.socialmedia.model;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Channel {
    private static final int MAX_CONTENT_LENGTH = 280;
    private final String name;
    private final Set<User> subscribers;
    private final List<Post> posts;

    public Channel(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Channel name cannot be null or empty");
        }
        this.name = name;
        this.subscribers = Collections.synchronizedSet(new HashSet<>());
        this.posts = new CopyOnWriteArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean subscribe(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return subscribers.add(user);
    }

    public boolean unsubscribe(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return subscribers.remove(user);
    }

    public void addPost(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }
        posts.add(post);
        notifySubscribers(post);
    }

    public void postContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("Content cannot exceed " + MAX_CONTENT_LENGTH + " characters");
        }
        
        Post post = new Post(content.trim());
        addPost(post);
    }

    private void notifySubscribers(Post post) {
        for (User subscriber : subscribers) {
            subscriber.receiveContent(post.toString());
        }
    }

    public Set<User> getSubscribers() {
        return Collections.unmodifiableSet(subscribers);
    }

    public List<Post> getPosts() {
        return Collections.unmodifiableList(posts);
    }

    public int getMaxContentLength() {
        return MAX_CONTENT_LENGTH;
    }
}
