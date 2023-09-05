package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;

@JsonSerialize(using = ResponseSerializer.class)
public final class LoginResponse implements ResponseSerializer.Response {

    private final ErrorReason errorReason;
    private final long id;
    private final long userId;

    public LoginResponse(ErrorReason errorReason) {
        this.errorReason = errorReason;
        id = -1;
        userId = -1;
    }

    public LoginResponse(long id, long userId) {
        errorReason = null;
        this.id = id;
        this.userId = userId;
    }

    public String getResponse() {
        if (errorReason != null) return Integer.toString(errorReason.code);
        return id + "," + userId;
    }

    public enum ErrorReason {
        LOGIN_FAILED(-1),
        BANNED(-12);

        private final int code;

        ErrorReason(int code) {
            this.code = code;
        }
    }
}
