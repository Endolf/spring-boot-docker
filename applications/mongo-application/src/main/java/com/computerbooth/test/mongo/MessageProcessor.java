package com.computerbooth.test.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;

@Configuration
public class MessageProcessor {
    private Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    private Queue inboundQueue;
    private MessageRepository repository;

    @Autowired
    public void setInboundQueue(Queue inboundQueue) {
        this.inboundQueue = inboundQueue;
    }

    @Autowired
    public void setRepository(MessageRepository repository) {
        this.repository = repository;
    }

    @Bean
    @Autowired
    public IntegrationFlow amqpInbound(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory, inboundQueue)
                    .messageConverter(messageConverter))
                .channel("amqpInboundChannel")
                .get();
    }

    @Bean
    public IntegrationFlow processMessage() {
        return IntegrationFlows.from("amqpInboundChannel")
                .<com.computerbooth.test.Message, Message>transform(source -> new Message(source))
                .<Message>handle((payload, headers) -> repository.save(payload))
                .<Message>handle(message -> logger.info("Processed message: {}", message.getPayload()))
                .get();
    }
}
