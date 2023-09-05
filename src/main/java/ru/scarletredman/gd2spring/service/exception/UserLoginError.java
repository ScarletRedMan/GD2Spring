package ru.scarletredman.gd2spring.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.scarletredman.gd2spring.controller.response.LoginResponse;

@Getter
@RequiredArgsConstructor
public class UserLoginError extends Error {

    private final LoginResponse.ErrorReason reason;
}
