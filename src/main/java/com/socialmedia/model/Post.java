package com.socialmedia.model;

import java.time.LocalDateTime;

public class Post {
    private final String content;
    private final LocalDateTime timestamp;

    public Post(String content) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", 
            timestamp.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")),
            content);
    }
}
