package ru.scarletredman.gd2spring.rabbit.response;

import org.springframework.lang.NonNull;

public abstract class EventMQResponse {

    private final String amqpRoutingKey;

    public EventMQResponse(@NonNull String routingKey) {
        amqpRoutingKey = routingKey;
    }

    public final String getAmqpRoutingKey() {
        return amqpRoutingKey;
    }
}
