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
public class Message extends com.computerbooth.test.Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
        this.timestamp = timestamp;
        this.message = message;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
