package ru.scarletredman.gd2spring.util;

import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JoinResponseUtil {

    public <T extends Key> String join(Map<T, Object> elements, String delimiter) {
        String[] buffer = new String[elements.size() * 2];

        int i = 0;
        for (var element : elements.keySet()) {
            buffer[i * 2] = element.getCode();
            buffer[i * 2 + 1] = elements.get(element).toString();

            i++;
        }

        return String.join(delimiter, buffer);
    }

    public interface Key {

        String getCode();
    }
}
