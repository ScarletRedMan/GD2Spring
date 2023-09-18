package ru.scarletredman.gd2spring.rabbit;

import ru.scarletredman.gd2spring.rabbit.response.EventResponse;

public interface MQEventPublisher {

    void publish(EventResponse response);
}
