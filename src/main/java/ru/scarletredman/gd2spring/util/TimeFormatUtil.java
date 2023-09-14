package ru.scarletredman.gd2spring.util;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeFormatUtil {

    public String formatBetween(Timestamp timestamp) {
        var now = Instant.now();
        var delta = Duration.between(timestamp.toInstant(), now);

        long days = delta.toDays();
        if (days != 0) {
            return days + (days != 1 ? " days" : " day");
        }

        long hours = delta.toHours() % 60;
        if (hours != 0) {
            return hours + (hours != 1 ? " hours" : " hour");
        }

        long minutes = delta.toMinutes() % 60;
        if (minutes != 0) {
            return minutes + (minutes != 1 ? " minutes" : " minute");
        }

        long seconds = delta.toSeconds() % 60;
        return seconds + (seconds != 1 ? " seconds" : " second");
    }
}
