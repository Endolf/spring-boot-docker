package com.computerbooth.test.cassandra;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table
public class Message {
    @PrimaryKey
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
        this.id = UUID.randomUUID();
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
