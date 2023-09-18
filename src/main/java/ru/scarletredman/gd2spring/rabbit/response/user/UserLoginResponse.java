package ru.scarletredman.gd2spring.rabbit.response.user;

import lombok.Getter;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.rabbit.response.EventResponse;

@Getter
public class UserLoginResponse extends EventResponse {

    private final long userId;
    private final String username;

    public UserLoginResponse(User user) {
        super("user.login");
        userId = user.getId();
        username = user.getUsername();
    }
}
