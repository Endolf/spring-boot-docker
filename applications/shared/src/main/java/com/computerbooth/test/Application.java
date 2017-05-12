package com.computerbooth.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@PropertySource("classpath:application.yml")
public class Application {
    @Value("${amqp.exchangeName:com.computerbooth.poc}")
    private String mqExchangeName;

    @Value("${amqp.routingKey:test}")
    private String mqRoutingKey;

    @Value("${spring.application.name}")
    private String name;

    @Bean
    @Autowired
    public MessageConverter messageConverter(ObjectMapper objectMapper){
        objectMapper.findAndRegisterModules();
        ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter();
        converter.addDelegate("application/json", new Jackson2JsonMessageConverter(objectMapper));
        return converter;
    }

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
                .enrichHeaders(h -> h.header("contentType", "application/json"))
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
