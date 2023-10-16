package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;

@JsonSerialize(using = ResponseSerializer.class)
public final class RemoveMessageResponse implements ResponseSerializer.Response {

    private final boolean error;

    private RemoveMessageResponse(boolean error) {
        this.error = error;
    }

    public static RemoveMessageResponse success() {
        return new RemoveMessageResponse(false);
    }

    public static RemoveMessageResponse error() {
        return new RemoveMessageResponse(true);
    }

    @Override
    public String getResponse() {
        return error ? "-1" : "1";
    }
}
