package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;

@Getter
@JsonSerialize(using = ResponseSerializer.class)
public enum DeleteAccountCommentResponse implements ResponseSerializer.Response {
    SUCCESS(1),
    FAILED(-1);

    private final int code;

    DeleteAccountCommentResponse(int code) {
        this.code = code;
    }

    @Override
    public String getResponse() {
        return Integer.toString(code);
    }
}
