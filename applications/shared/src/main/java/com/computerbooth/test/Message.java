package com.computerbooth.test;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class Message implements Serializable {
    private UUID id;
    private Instant timestamp;
    private String message;

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
        return timestamp + ": " + id + ": " + message;
    }
}
