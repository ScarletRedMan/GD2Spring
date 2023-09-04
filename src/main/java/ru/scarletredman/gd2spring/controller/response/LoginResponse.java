package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;

@JsonSerialize(using = ResponseSerializer.class)
public final class LoginResponse implements ResponseSerializer.Response {

    private final ErrorReason errorReason;
    private final int id;
    private final int userId;

    public LoginResponse(ErrorReason errorReason) {
        this.errorReason = errorReason;
        id = -1;
        userId = -1;
    }

    public LoginResponse(int id, int userId) {
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
