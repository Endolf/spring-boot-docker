package com.computerbooth.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@IntegrationComponentScan
@PropertySource("classpath:application.yml")
public class Application {

    private final Logger logger = LoggerFactory.getLogger(Application.class);

    @Value("${amqp.exchangeName:com.computerbooth.poc}")
    private String mqExchangeName;

    @Value("${amqp.routingKey:test}")
    private String mqRoutingKey;

    @Value("${spring.application.name}")
    private String name;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(mqExchangeName);
    }

    @Bean
    public Queue inboundQueue() {
        return new Queue(mqExchangeName + "." + mqRoutingKey + "." + name);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(inboundQueue()).to(topicExchange()).with(mqRoutingKey);
    }

    @Bean
    @Autowired
    public IntegrationFlow amqpOutbound(AmqpTemplate amqpTemplate) {
        return IntegrationFlows.from(amqpOutboundChannel())
                .handle(Amqp.outboundAdapter(amqpTemplate)
                        .exchangeName(mqExchangeName)
                        .routingKey(mqRoutingKey))
                .get();
    }

    @Bean
    public MessageChannel amqpOutboundChannel() {
        return new DirectChannel();
    }

    @MessagingGateway(defaultRequestChannel = "amqpOutboundChannel")
    public interface AMQPEndpoint {
        void sendToQueue(Message data);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
