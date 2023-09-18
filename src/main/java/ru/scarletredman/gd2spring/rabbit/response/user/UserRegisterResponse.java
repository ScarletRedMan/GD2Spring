package ru.scarletredman.gd2spring.rabbit.response.user;

import lombok.Getter;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.rabbit.response.EventResponse;

@Getter
public class UserRegisterResponse extends EventResponse {

    private final long userId;
    private final String username;
    private final String email;

    public UserRegisterResponse(User user) {
        super("user.register");
        userId = user.getId();
        username = user.getUsername();
        email = user.getEmail();
    }
}
