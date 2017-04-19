package com.computerbooth.test;

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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP(3)")
    private Instant timestamp;
    @Column(updatable = false, nullable = false)
    private String message;

    protected Message() {}

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
}
