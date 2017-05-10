package com.computerbooth.test;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import org.springframework.util.StringUtils;

@SpringBootApplication
@IntegrationComponentScan
@PropertySource("classpath:application.yml")
public class Application {

    @Value("${rabbitMQServerAddresses:127.0.0.1}")
    private String rabbitMQServerAddresses;

    @Value("${rabbitMQExchangeName:}")
    private String rabbitMQExchangeName;

    @Value("${rabbitMQRoutingKey:test}")
    private String rabbitMQRoutingKey;

    @Value("${rabbitMQUsername:}")
    private String rabbitMQUsername;

    @Value("${rabbitMQPassword:}")
    private String rabbitMQPassword;

    @Bean
    AmqpTemplate amqpTemplate() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rabbitMQServerAddresses);
        if(!StringUtils.isEmpty(rabbitMQUsername)) {
            connectionFactory.setUsername(rabbitMQUsername);
            if(!StringUtils.isEmpty(rabbitMQPassword)) {
                connectionFactory.setPassword(rabbitMQPassword);
            }
        }
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    @Autowired
    public IntegrationFlow amqpOutbound(AmqpTemplate amqpTemplate) {
        return IntegrationFlows.from(amqpOutboundChannel())
                .handle(Amqp.outboundAdapter(amqpTemplate)
                        .exchangeName(rabbitMQExchangeName)
                        .routingKey(rabbitMQRoutingKey))
                .get();
    }

    @Bean
    public MessageChannel amqpOutboundChannel() {
        return new DirectChannel();
    }

    @MessagingGateway(defaultRequestChannel = "amqpOutboundChannel")
    public interface AMQPGateway {
        void sendToQueue(Message data);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
