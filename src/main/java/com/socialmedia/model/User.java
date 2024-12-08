package com.socialmedia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class User {
    private final String username;
    private final List<String> receivedContent;

    public User(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username.trim();
        this.receivedContent = Collections.synchronizedList(new ArrayList<>());
    }

    public String getUsername() {
        return username;
    }

    public void receiveContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        receivedContent.add(0, content); // Add new content at the beginning
    }

    public List<String> getReceivedContent() {
        return Collections.unmodifiableList(receivedContent);
    }

    public boolean hasReceivedContent(String content) {
        return content != null && receivedContent.contains(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return username;
    }
}
