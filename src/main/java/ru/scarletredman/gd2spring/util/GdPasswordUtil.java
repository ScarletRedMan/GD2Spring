package ru.scarletredman.gd2spring.util;

import lombok.experimental.UtilityClass;
import org.apache.tomcat.util.codec.binary.Base64;

@UtilityClass
public class GdPasswordUtil {

    public final String DEFAULT_KEY = "37526";

    public String xor(String input, String key) {
        byte[] txt = input.getBytes();
        byte[] keyBytes = key.getBytes();
        byte[] res = new byte[input.length()];

        for (int i = 0; i < txt.length; i++) {
            res[i] = (byte) (txt[i] ^ keyBytes[i % keyBytes.length]);
        }

        return new String(res);
    }

    public String xor(String input) {
        return xor(input, DEFAULT_KEY);
    }

    public String gjpEncode(String password) {
        return Base64.encodeBase64String(xor(password).getBytes());
    }

    public String gjpDecode(String value) {
        return xor(new String(Base64.decodeBase64(value)));
    }
}
