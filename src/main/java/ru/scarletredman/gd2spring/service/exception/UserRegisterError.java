package ru.scarletredman.gd2spring.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.scarletredman.gd2spring.controller.response.RegisterResponse;

@Getter
@RequiredArgsConstructor
public class UserRegisterError extends Error {

    private final RegisterResponse reason;
}
