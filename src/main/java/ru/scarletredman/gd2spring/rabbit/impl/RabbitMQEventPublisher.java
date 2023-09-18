package ru.scarletredman.gd2spring.rabbit.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import ru.scarletredman.gd2spring.rabbit.MQEventPublisher;
import ru.scarletredman.gd2spring.rabbit.response.EventMQResponse;

@RequiredArgsConstructor
public class RabbitMQEventPublisher implements MQEventPublisher {

    private final AmqpTemplate amqp;
    private final ObjectMapper json;
    private final Exchange exchange;

    @Override
    public void publish(EventMQResponse response) {
        try {
            amqp.convertAndSend(exchange.getName(), response.getAmqpRoutingKey(), json.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
