package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.nio.charset.StandardCharsets;
import java.util.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;
import ru.scarletredman.gd2spring.model.dto.GDLevelDTO;
import ru.scarletredman.gd2spring.service.type.LevelListPage;
import ru.scarletredman.gd2spring.util.JoinResponseUtil;

@Getter
@JsonSerialize(using = ResponseSerializer.class)
public final class GetLevelsResponse implements ResponseSerializer.Response {

    private static final String DELIMITER_LEVELS = "|";
    private static final String DELIMITER_USERS = "|";
    private static final String DELIMITER_SONGS = "~:~";

    private final List<LevelStat> levelStats = new ArrayList<>();
    private final List<UserStat> userStats = new ArrayList<>();
    private final List<SongStat> songStats = new ArrayList<>();
    private final List<ChainData> chainData = new ArrayList<>();
    private int offset;
    private long total;

    public GetLevelsResponse(LevelListPage page) {
        init(page);
    }

    private void init(LevelListPage page) {
        offset = page.offset();
        total = page.total();

        for (var level : page.levels()) {
            levelStats.add(new LevelStat(level));
            userStats.add(new UserStat(level.getUser()));
            songStats.add(new SongStat(level));
            chainData.add(new ChainData(
                    level.getId(), level.getRate().getStars(), level.getRate().isVerifiedCoins()));
        }
    }

    @Override
    public String getResponse() {
        String levels = String.join(
                DELIMITER_LEVELS,
                levelStats.stream().map(LevelStat::getResponse).toList());

        String users = String.join(
                DELIMITER_USERS, userStats.stream().map(UserStat::getResponse).toList());

        String songs = String.join(
                DELIMITER_SONGS, songStats.stream().map(SongStat::getResponse).toList());

        String pageInfo = total + ":" + (offset * 10) + ":10";
        String hash = generateHash();

        return levels + "#" + users + "#" + songs + "#" + pageInfo + "#" + hash;
    }

    private String generateHash() {
        var sb = new StringBuilder();
        for (var chain : chainData) {
            chain.appendTo(sb);
        }

        return DigestUtils.sha1Hex(sb.append("xI25fpAapCQg").toString());
    }

    @Getter
    @JsonSerialize(using = ResponseSerializer.class)
    public static class LevelStat implements ResponseSerializer.Response {

        private static final String DELIMITER = ":";

        private final Map<Key, Object> stats = new EnumMap<>(Key.class);

        public LevelStat(GDLevelDTO level) {
            init(level);
        }

        private void init(GDLevelDTO level) {
            stats.put(Key.ID, level.getId());
            stats.put(Key.NAME, level.getName());
            setDescription(level.getDescription());
            stats.put(Key.VERSION, level.getVersion());
            stats.put(Key.OWNER_USER_ID, level.getUser().id());
            stats.put(Key.UNKNOWN1, 10);
            stats.put(Key.DIFFICULTY, level.getRate().getDifficulty().getGdDiff());
            stats.put(Key.DOWNLOADS, level.getDownloads());
            stats.put(Key.AUDIO_TRACK, level.getAudioTrack());
            stats.put(Key.GAME_VERSION, 21);
            stats.put(Key.LIKES, level.getLikes());
            stats.put(Key.LENGTH, level.getFilters().getLength().getCode());
            stats.put(Key.IS_DEMON, level.getRate().getDifficulty().isDemon() ? 1 : 0);
            stats.put(Key.STARS, level.getRate().getStars());
            stats.put(Key.IS_FEATURED, level.getRate().isFeatured() ? 1 : 0);
            stats.put(Key.IS_AUTO, level.getRate().getDifficulty().isAuto() ? 1 : 0);
            stats.put(Key.ORIGINAL, level.getOriginalLevel() == null ? 0 : level.getOriginalLevel());
            stats.put(Key.FOR_TWO_PLAYERS, level.getFilters().isTwoPlayers() ? 1 : 0);
            stats.put(Key.SONG_ID, level.getSong() == null ? 0 : level.getSong().getId());
            stats.put(Key.COINS, level.getRate().getCoins());
            stats.put(Key.VERIFIED_COINS, level.getRate().isVerifiedCoins() ? 1 : 0);
            stats.put(Key.REQESTED_STARS, level.getRate().getRequestedStars());
            stats.put(Key.IS_LDM, level.getFilters().isLowDetailMode() ? 1 : 0);
            stats.put(Key.EPIC, level.getRate().isEpic());
            stats.put(Key.DEMON_DIFFICULTY, level.getRate().getDifficulty().getDemonDiff());
            stats.put(Key.OBJECTS, level.getObjects());
            stats.put(Key.UNKNOWN2, 1);
            stats.put(Key.UNKNOWN3, 2);

            // todo: gauntlet
        }

        public void setDescription(String description) {
            stats.put(Key.DESCRIPTION, Base64.encodeBase64String(description.getBytes(StandardCharsets.UTF_8)));
        }

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
            VERIFIED_COINS("38"),
            REQESTED_STARS("39"),
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

    @JsonSerialize(using = ResponseSerializer.class)
    public record UserStat(GDLevelDTO.User user) implements ResponseSerializer.Response {

        @Override
        public String getResponse() {
            return user.id() + ":" + user.username() + ":" + user.id();
        }
    }

    @Getter
    @JsonSerialize(using = ResponseSerializer.class)
    public static class SongStat implements ResponseSerializer.Response {

        private static final String DELIMITER = "~|~";

        private final Map<Key, Object> stats = new EnumMap<>(Key.class);

        public SongStat(GDLevelDTO level) {
            init(level);
        }

        private void init(GDLevelDTO level) {
            var song = level.getSong();
            if (song == null) return;

            stats.put(Key.ID, song.getId());
            setName(song.getName());
            stats.put(Key.AUTHOR_ID, song.getAuthorId());
            stats.put(Key.AUTHOR_NAME, song.getAuthorName());
            stats.put(Key.SIZE, song.getSize());
            stats.put(Key.UNKNOWN1, "");
            stats.put(Key.UNKNOWN2, "");
            stats.put(Key.UNKNOWN3, 1);
            stats.put(Key.DOWNLOAD_URL, song.getDownloadUrl());
        }

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

    public record ChainData(long levelId, int stars, boolean verifiedCoins) {

        public void appendTo(StringBuilder sb) {
            char[] ch = Long.toString(levelId).toCharArray();
            sb.append(ch[0]).append(ch[ch.length - 1]).append(stars).append(verifiedCoins ? 1 : 0);
        }
    }
}
