package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.util.JoinResponseUtil;

@Getter
@Setter
@JsonSerialize(using = ResponseSerializer.class)
public final class UserInfoResponse implements ResponseSerializer.Response {

    private static final String SEPARATOR = "|";

    private final boolean error;
    private final User user;
    private final boolean self;
    private final Map<Stat, Object> stats = new EnumMap<>(Stat.class);

    private UserInfoResponse() {
        this.error = true;
        this.user = null;
        this.self = false;
    }

    public UserInfoResponse(@NonNull User user, boolean self) {
        this.error = false;
        this.user = user;
        this.self = self;
        init(user);
    }

    public static UserInfoResponse errorResponse() {
        return new UserInfoResponse();
    }

    private void init(User user) {
        stats.put(Stat.NAME, user.getUsername());
        stats.put(Stat.CREATOR_POINTS, user.getCreatorPoints());
        stats.put(Stat.PLAYER_ID, user.getId());
        stats.put(Stat.ACCOUNT_ID, user.getId());
        stats.put(Stat.SHOW_RANK, user.isRatingBanned() ? 0 : 1);
        stats.put(Stat.RANK, user.getRating());

        stats.put(Stat.STARS, user.getStars());
        stats.put(Stat.DEMONS, user.getDemons());
        stats.put(Stat.COINS, user.getCoins());
        stats.put(Stat.USER_COINS, user.getUserCoins());

        var skin = user.getSkin();
        stats.put(Stat.FIRST_COLOR, skin.getFirstColor());
        stats.put(Stat.SECOND_COLOR, skin.getSecondColor());
        stats.put(Stat.ACC_ICON, skin.getAccIcon());
        stats.put(Stat.ACC_BALL, skin.getAccBall());
        stats.put(Stat.ACC_BIRD, skin.getAccBird());
        stats.put(Stat.ACC_DART, skin.getAccDart());
        stats.put(Stat.ACC_GLOW, skin.getAccGlow());
        stats.put(Stat.ACC_EXPLOSION, skin.getAccExplosion());
        stats.put(Stat.ACC_ROBOT, skin.getAccRobot());
        stats.put(Stat.ACC_SHIP, skin.getAccShip());
        stats.put(Stat.ACC_SPIDER, skin.getAccSpider());

        stats.put(Stat.YOUTUBE_URL, user.getYoutubeUrl());
        stats.put(Stat.TWITCH_URL, user.getTwitchUrl());
        stats.put(Stat.TWITTER_URL, user.getTwitterUrl());

        stats.put(Stat.FRIEND_STATE, 0); // todo: is friend

        var settings = user.getUserSettings();
        stats.put(
                Stat.ALLOW_FRIEND_REQ_SETTING,
                settings.getAllowFriendRequestsFrom().getValue());
        stats.put(Stat.ALLOW_MESSAGES_SETTING, settings.getAllowMessagesFrom().getValue());
        stats.put(
                Stat.VISIBLE_COMMENTS_HISTORY_SETTING,
                settings.getShowCommentHistoryTo().getValue());

        stats.put(Stat.BADGE, Badge.NONE); // todo: moderator status

        if (!self) return;
        stats.put(Stat.MESSAGES_COUNT, 0); // todo: messages
        stats.put(Stat.FRIENDS_REQ_COUNT, 0); // todo: friends
        stats.put(Stat.FRIENDS_COUNT, 0); // todo: friends
    }

    public void setBadge(@NonNull Badge badge) {
        stats.put(Stat.BADGE, badge.getCode());
    }

    @Override
    public String getResponse() {
        if (error) {
            return "-1";
        }

        return JoinResponseUtil.join(stats, ":");
    }

    @Getter
    public enum Stat implements JoinResponseUtil.Key {
        NAME("1"),
        PLAYER_ID("2"),
        STARS("3"),
        DEMONS("4"),
        CREATOR_POINTS("8"),
        FIRST_COLOR("10"),
        SECOND_COLOR("11"),
        COINS("13"),
        ACCOUNT_ID("16"),
        USER_COINS("17"),
        ALLOW_MESSAGES_SETTING("18"),
        ALLOW_FRIEND_REQ_SETTING("19"),
        YOUTUBE_URL("20"),
        ACC_ICON("21"),
        ACC_SHIP("22"),
        ACC_BALL("23"),
        ACC_BIRD("24"),
        ACC_DART("25"),
        ACC_ROBOT("26"),
        ACC_GLOW("28"),
        RANK("30"),
        FRIEND_STATE("31"),
        MESSAGES_COUNT("38"), // player == target
        FRIENDS_REQ_COUNT("39"), // player == target
        FRIENDS_COUNT("40"), // player == target
        ACC_SPIDER("43"),
        TWITTER_URL("44"),
        TWITCH_URL("45"),
        DIAMONDS("46"),
        SHOW_RANK("29"),
        ACC_EXPLOSION("47"),
        BADGE("49"),
        VISIBLE_COMMENTS_HISTORY_SETTING("50");

        private final String code;

        Stat(String code) {
            this.code = code;
        }
    }

    @Getter
    public enum Badge {
        NONE(0),
        MODER(1),
        ELDER_MODER(2);

        private final int code;

        Badge(int code) {
            this.code = code;
        }
    }
}
