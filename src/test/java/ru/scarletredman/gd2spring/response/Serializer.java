package ru.scarletredman.gd2spring.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;

public final class Serializer {

    private static final ObjectMapper serializer = new ObjectMapper();

    private Serializer() {}

    public static String serialize(ResponseSerializer.Response response) {
        try {
            return serializer.writeValueAsString(response);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
