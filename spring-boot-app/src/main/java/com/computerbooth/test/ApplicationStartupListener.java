package com.computerbooth.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    private final Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);
    private MessageRepository messageRepository;

    @Autowired
    public void setMessageRepository(MessageRepository repo) {
        this.messageRepository = repo;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        StringBuilder messageTextBuilder = new StringBuilder()
                .append("Application started: \"")
                .append(event.getApplicationContext().getId())
                .append("\" at ")
                .append(event.getTimestamp())
                .append(" by ")
                .append(event.getSource());
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            messageTextBuilder.append(" on ")
                    .append(hostname);
        } catch(UnknownHostException e) {
            e.printStackTrace();
        }
        String messageText = messageTextBuilder.toString();
        logger.info(messageText);
        messageRepository.save(new Message(Instant.now(), messageText));
    }
}
