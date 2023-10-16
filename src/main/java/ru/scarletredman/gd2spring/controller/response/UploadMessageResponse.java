package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;

@JsonSerialize(using = ResponseSerializer.class)
public class UploadMessageResponse implements ResponseSerializer.Response {

    private final boolean error;

    private UploadMessageResponse(boolean error) {
        this.error = error;
    }

    public static UploadMessageResponse success() {
        return new UploadMessageResponse(false);
    }

    public static UploadMessageResponse error() {
        return new UploadMessageResponse(true);
    }

    @Override
    public String getResponse() {
        return error ? "-1" : "1";
    }
}
