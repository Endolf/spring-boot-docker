package com.computerbooth.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
public class LoggingMessageProcessor {
    private final Logger logger = LoggerFactory.getLogger(LoggingMessageProcessor.class);

    @Bean
    @ConditionalOnProperty(name="spring.application.name", havingValue = "Core test application")
    public IntegrationFlow processMessage() {
        return IntegrationFlows.from("jmsInboundChannel")
                .handle(message -> logger.info("Processed message: {}", message.getPayload()))
                .get();
    }
}
