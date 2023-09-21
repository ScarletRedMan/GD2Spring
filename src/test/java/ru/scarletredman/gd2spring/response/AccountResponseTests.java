package ru.scarletredman.gd2spring.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.scarletredman.gd2spring.controller.response.LoginResponse;
import ru.scarletredman.gd2spring.model.User;

public class AccountResponseTests {

    private static final String LOGIN_SUCCESS_RESPONSE = "1,1";
    private static final String LOGIN_FAIL_RESPONSE =
            Integer.toString(LoginResponse.ErrorReason.LOGIN_FAILED.getCode());
    private static final String LOGIN_BANNED_RESPONSE = Integer.toString(LoginResponse.ErrorReason.BANNED.getCode());

    private User createTestUser() {
        var user = new User("test", "qwerty", "m@m.m");
        user.setId(1L);
        return user;
    }

    @Test
    void testLoginResponse() {
        var user = createTestUser();

        var successResponse = new LoginResponse(user.getId(), user.getId());
        Assertions.assertEquals(LOGIN_SUCCESS_RESPONSE, Serializer.serialize(successResponse));

        var failResponse = new LoginResponse(LoginResponse.ErrorReason.LOGIN_FAILED);
        Assertions.assertEquals(LOGIN_FAIL_RESPONSE, Serializer.serialize(failResponse));

        var bannedResponse = new LoginResponse(LoginResponse.ErrorReason.BANNED);
        Assertions.assertEquals(LOGIN_BANNED_RESPONSE, Serializer.serialize(bannedResponse));
    }
}
