package ru.scarletredman.gd2spring.rabbit.response.user;

import lombok.Getter;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.rabbit.response.EventMQResponse;

@Getter
public class UserRegisterMQResponse extends EventMQResponse {

    private final long userId;
    private final String username;
    private final String email;

    public UserRegisterMQResponse(User user) {
        super("user.register");
        userId = user.getId();
        username = user.getUsername();
        email = user.getEmail();
    }
}
