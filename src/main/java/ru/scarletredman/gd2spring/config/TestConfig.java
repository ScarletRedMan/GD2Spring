package ru.scarletredman.gd2spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ru.scarletredman.gd2spring.model.UserComment;
import ru.scarletredman.gd2spring.service.UserCommentService;
import ru.scarletredman.gd2spring.service.UserService;

@Configuration
@RequiredArgsConstructor
public class TestConfig {

    private final UserService userService;
    private final UserCommentService userCommentService;

    @Autowired
    void createTestUser(boolean debugMode) {
        if (!debugMode) return;

        var user = userService.registerUser("test", "qwerty", "m@m.m");
        userCommentService.writeComment(new UserComment(user, "Hello world!"));
    }
}
