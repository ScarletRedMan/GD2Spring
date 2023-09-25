package ru.scarletredman.gd2spring.controller.response;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;
import ru.scarletredman.gd2spring.util.JoinResponseUtil;

@Getter
public final class GetLevelsResponse implements ResponseSerializer.Response {

    private static final String DELIMITER_LEVELS = "|";
    private static final String DELIMITER_USERS = "|";
    private static final String DELIMITER_SONGS = "~:~";

    private final List<LevelStat> levelStats = new ArrayList<>();
    private final List<UserStat> userStats = new ArrayList<>();
    private final List<SongStat> songStats = new ArrayList<>();

    public GetLevelsResponse() {
        init();
    }

    private void init() {}

    @Override
    public String getResponse() {
        String levels = String.join(
                DELIMITER_LEVELS,
                levelStats.stream().map(LevelStat::getResponse).toList());

        String users = String.join(
                DELIMITER_USERS, userStats.stream().map(UserStat::getResponse).toList());

        String songs = String.join(
                DELIMITER_SONGS, songStats.stream().map(SongStat::getResponse).toList());

        long total = 1;
        int offset = 1;

        String hash = generateHash();

        return levels + "#" + users + "#" + songs + "#" + total + "#" + offset + "#10" + "#" + hash;
    }

    private String generateHash() {
        var sb = new StringBuilder();

        return DigestUtils.sha1Hex(sb.append("xI25fpAapCQg").toString());
    }

    @Getter
    public static class LevelStat implements ResponseSerializer.Response {

        private static final String DELIMITER = ":";

        private final Map<Key, Object> stats = new EnumMap<>(Key.class);

        public LevelStat() {
            init();
        }

        private void init() {}

        @Override
        public String getResponse() {
            return JoinResponseUtil.join(stats, DELIMITER);
        }

        @RequiredArgsConstructor
        public enum Key implements JoinResponseUtil.Key {
            ID("1"),
            NAME("2"),
            DESCRIPTION("3"),
            VERSION("5"),
            OWNER_USER_ID("6"),
            UNKNOWN1("8"), // always 10
            DIFFICULTY("9"),
            DOWNLOADS("10"),
            AUDIO_TRACK("12"), // build-in soundtrack is
            GAME_VERSION("13"),
            LIKES("14"),
            LENGTH("15"),
            IS_DEMON("17"), // maybe
            STARS("18"),
            IS_FEATURED("19"),
            IS_AUTO("25"),
            ORIGINAL("30"),
            FOR_TWO_PLAYERS("31"),
            SONG_ID("35"),
            COINS("37"),
            REQUIRED_STARS("39"),
            IS_LDM("40"),
            EPIC("42"),
            DEMON_DIFFICULTY("43"),
            GAUNTLET("44"),
            OBJECTS("45"),
            UNKNOWN2("46"), // always 1
            UNKNOWN3("47"), // always 2
            ;

            private final String code;

            @Override
            public String getCode() {
                return code;
            }
        }
    }

    public record UserStat(UserEntry user) implements ResponseSerializer.Response {

        @Override
        public String getResponse() {
            return user.id() + ":" + user.username() + ":" + user.id();
        }

        public record UserEntry(long id, String username) {}
    }

    @Getter
    public static class SongStat implements ResponseSerializer.Response {

        private static final String DELIMITER = "~|~";

        private final Map<Key, Object> stats = new EnumMap<>(Key.class);

        public SongStat() {
            init();
        }

        private void init() {}

        public void setName(String name) {
            stats.put(Key.NAME, name.replaceAll("#", ""));
        }

        @Override
        public String getResponse() {
            return JoinResponseUtil.join(stats, DELIMITER);
        }

        @RequiredArgsConstructor
        public enum Key implements JoinResponseUtil.Key {
            ID("1"),
            NAME("2"),
            AUTHOR_ID("3"),
            AUTHOR_NAME("4"),
            SIZE("5"),
            UNKNOWN1("6"), // always empty
            UNKNOWN2("7"), // always empty
            UNKNOWN3("8"), // always 1
            DOWNLOAD_URL("10"),
            ;

            private final String code;

            @Override
            public String getCode() {
                return code;
            }
        }
    }
}
