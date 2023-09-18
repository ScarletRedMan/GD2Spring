package ru.scarletredman.gd2spring.rabbit;

import ru.scarletredman.gd2spring.rabbit.response.EventMQResponse;

public interface MQEventPublisher {

    void publish(EventMQResponse response);
}
