package ru.scarletredman.gd2spring.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;

public class Sha256HashPassword implements HashPassword {

    @Override
    public String hash(String input, String salt) {
        var password = input + "|" + salt;

        try {
            return Hex.encodeHexString(
                    MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
