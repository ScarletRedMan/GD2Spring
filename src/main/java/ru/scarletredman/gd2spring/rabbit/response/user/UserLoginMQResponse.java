package ru.scarletredman.gd2spring.rabbit.response.user;

import lombok.Getter;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.rabbit.response.EventMQResponse;

@Getter
public class UserLoginMQResponse extends EventMQResponse {

    private final long userId;
    private final String username;

    public UserLoginMQResponse(User user) {
        super("user.login");
        userId = user.getId();
        username = user.getUsername();
    }
}
