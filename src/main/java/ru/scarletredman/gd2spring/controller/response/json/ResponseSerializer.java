package ru.scarletredman.gd2spring.controller.response.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.RawSerializer;
import ru.scarletredman.gd2spring.controller.response.LoginResponse;

import java.io.IOException;

public class ResponseSerializer extends RawSerializer<ResponseSerializer.Response> {

    public ResponseSerializer() {
        super(Response.class);
    }

    @Override
    public void serialize(Response value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeRawValue(value.getResponse());
    }

    public interface Response {

        String getResponse();
    }
}
