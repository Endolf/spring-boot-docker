package com.computerbooth.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.MessageChannel;

import javax.jms.ConnectionFactory;

@Configuration
@EnableIntegration
public class SIConfiguration {

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
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    @Autowired
    public IntegrationFlow jmsOutbound(JmsTemplate template) {
        return IntegrationFlows.from(jmsOutboundChannel())
                .enrichHeaders(h -> h.header("contentType", "application/json"))
                .handle(Jms.outboundAdapter(template)
                        .destination("test"))
                .get();
    }

    @Bean
    public MessageChannel jmsOutboundChannel() {
        return new DirectChannel();
    }

    @MessagingGateway(defaultRequestChannel = "jmsOutboundChannel")
    public interface MessagingAdapter {
        void sendMessage(Message data);
    }

    @Bean
    public IntegrationFlow errorHandler() {
        return IntegrationFlows.from("errorChannel").log().get();
    }

    @Bean
    @Autowired
    public IntegrationFlow jmsInbound(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        return IntegrationFlows.from(Jms.messageDrivenChannelAdapter(connectionFactory)
                    .destination("test").jmsMessageConverter(messageConverter).errorChannel("errorChannel"))
                .channel("jmsInboundChannel")
                .get();
    }
}
