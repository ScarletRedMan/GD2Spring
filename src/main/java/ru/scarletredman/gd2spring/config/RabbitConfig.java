package ru.scarletredman.gd2spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.scarletredman.gd2spring.rabbit.MQEventPublisher;
import ru.scarletredman.gd2spring.rabbit.impl.RabbitMQEventPublisher;

@Configuration
@EnableRabbit
public class RabbitConfig {

    @Bean
    ConnectionFactory connectionFactory() {
        var factory = new CachingConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("test");
        factory.setPassword("test");
        return factory;
    }

    @Bean
    Exchange eventExchange() {
        return new TopicExchange("gd2spring-event");
    }

    @Bean
    AmqpTemplate amqpTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    @Autowired
    MQEventPublisher eventPublisher(ObjectMapper objectMapper) {
        return new RabbitMQEventPublisher(amqpTemplate(), objectMapper, eventExchange());
    }
}
