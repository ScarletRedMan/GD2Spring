package ru.scarletredman.gd2spring.controller.response.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.RawSerializer;
import ru.scarletredman.gd2spring.controller.response.LoginResponse;

import java.io.IOException;

public class LoginSerializer extends RawSerializer<LoginResponse> {

    public LoginSerializer() {
        super(LoginResponse.class);
    }

    @Override
    public void serialize(LoginResponse value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeRawValue(value.getResponse());
    }
}
