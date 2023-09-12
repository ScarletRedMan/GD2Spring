package ru.scarletredman.gd2spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.scarletredman.gd2spring.util.GdPasswordUtil;

public class GdPasswordTests {

    @ParameterizedTest
    @ValueSource(
            strings = {
                "",
                "hello",
                "sssssssssssssssssssssssssss",
                "39ur92jfdi2gh3-fsi834hsd",
                GdPasswordUtil.DEFAULT_KEY
            })
    void testXor(String input) {
        Assertions.assertEquals(input, GdPasswordUtil.xor(GdPasswordUtil.xor(input)));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "",
                "hello",
                "sssssssssssssssssssssssssss",
                "39ur92jfdi2gh3-fsi834hsd",
                GdPasswordUtil.DEFAULT_KEY
            })
    void testGjp(String sourceValue) {
        Assertions.assertEquals(sourceValue, GdPasswordUtil.gjpDecode(GdPasswordUtil.gjpEncode(sourceValue)));
    }
}
