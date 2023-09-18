package ru.scarletredman.gd2spring.rabbit.response;

import org.springframework.lang.NonNull;

public abstract class EventResponse {

    private final String amqpRoutingKey;

    public EventResponse(@NonNull String routingKey) {
        amqpRoutingKey = routingKey;
    }

    public final String getAmqpRoutingKey() {
        return amqpRoutingKey;
    }
}
