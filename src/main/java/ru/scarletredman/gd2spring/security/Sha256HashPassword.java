package ru.scarletredman.gd2spring.security;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Sha256HashPassword implements HashPassword {

    @Override
    public String hash(String input) {
        try {
            return Hex.encodeHexString(MessageDigest.getInstance("SHA-256").digest(input.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
