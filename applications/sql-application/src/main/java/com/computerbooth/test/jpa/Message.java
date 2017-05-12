package com.computerbooth.test.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Message implements Serializable {
    private static final long serialVersionUID = -8630559334559235690L;
    @Id
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP(3)")
    private Instant timestamp;
    @Column(updatable = false, nullable = false)
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
