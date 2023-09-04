package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RegisterResponse {
    SUCCESS(1),
    UNKNOWN_ERROR(-1),
    USERNAME_IS_ALREADY_IN_USE(-2),
    EMAIL_IS_ALREADY_IN_USE(-3),
    INVALID_USERNAME(-4),
    PASSWORD_IS_INVALID(-5),
    EMAIL_IS_INVALID(-6),
    PASSWORDS_DO_NOT_MATCH(-7),
    TOO_SHORT_PASSWORD(-8),
    TOO_SHORT_USERNAME(-9);

    private final int code;

    RegisterResponse(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
