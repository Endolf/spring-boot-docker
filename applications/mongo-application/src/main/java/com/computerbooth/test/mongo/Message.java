package com.computerbooth.test.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document
public class Message {
    @Id
    private UUID id;
    private Instant timestamp;
    private String message;

    protected Message() {}

    protected Message(com.computerbooth.test.Message message) {
        this.id = message.getId();
        this.timestamp = message.getTimestamp();
        this.message = message.getMessage();
    }

    public Message(Instant timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Timestamp: " + getTimestamp() + ", ID: " + getId() + ", message: \"" + getMessage() + "\"";
    }
}
