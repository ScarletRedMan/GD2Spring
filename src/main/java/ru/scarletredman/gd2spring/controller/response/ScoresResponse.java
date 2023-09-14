package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;
import ru.scarletredman.gd2spring.model.dto.UserScoreDTO;

@JsonSerialize(using = ResponseSerializer.class)
public final class ScoresResponse implements ResponseSerializer.Response {

    private static final String SEPARATOR = "|";

    private final List<UserScoreDTO> players;

    public ScoresResponse(List<UserScoreDTO> players) {
        this.players = players;
    }

    @Override
    public String getResponse() {
        String[] result = new String[players.size()];
        int i = 0;
        for (var player : players) {
            result[i++] = new UserScoreSerializer(player).toString();
        }

        return String.join(SEPARATOR, result);
    }

    public static class UserScoreSerializer {

        private final UserScoreDTO dto;
        private final Map<ScoreElement, Object> elements = new EnumMap<>(ScoreElement.class);

        public UserScoreSerializer(UserScoreDTO dto) {
            this.dto = dto;
            init();
        }

        private void init() {
            elements.put(ScoreElement.NAME, dto.getUsername());
            elements.put(ScoreElement.PLAYER_ID, dto.getId());
            elements.put(ScoreElement.STARS, dto.getStars());
            elements.put(ScoreElement.DEMONS, dto.getDemons());
            elements.put(ScoreElement.TOP_POSITION, dto.getRank());
            elements.put(ScoreElement.ACCOUNT_ID1, dto.getId());
            elements.put(ScoreElement.ACCOUNT_ID2, dto.getId());
            elements.put(ScoreElement.CREATOR_POINTS, dto.getCreatorPoints());
            elements.put(ScoreElement.SKIN_ICON, dto.getSkinIcon());
            elements.put(ScoreElement.SKIN_FIRST_COLOR, dto.getSkinFirstColor());
            elements.put(ScoreElement.SKIN_SECOND_COLOR, dto.getSkinSecondColor());
            elements.put(ScoreElement.COINS, dto.getCoins());
            elements.put(ScoreElement.SKIN_ICON_TYPE, dto.getSkinIconType());
            elements.put(ScoreElement.SKIN_SPECIAL, dto.getSkinSpecial());
            elements.put(ScoreElement.USER_COINS, dto.getUserCoins());
            elements.put(ScoreElement.DIAMONDS, dto.getDiamonds());
        }

        @Override
        public String toString() {
            String[] buffer = new String[elements.size() * 2];

            int i = 0;
            for (var element : elements.keySet()) {
                buffer[i * 2] = element.getCode();
                buffer[i * 2 + 1] = elements.get(element).toString();

                i++;
            }

            return String.join(":", buffer);
        }
    }

    @Getter
    public enum ScoreElement {
        NAME("1"),
        PLAYER_ID("2"),
        STARS("3"),
        DEMONS("4"),
        TOP_POSITION("6"),
        ACCOUNT_ID1("7"),
        CREATOR_POINTS("8"),
        SKIN_ICON("9"),
        SKIN_FIRST_COLOR("10"),
        SKIN_SECOND_COLOR("11"),
        COINS("13"),
        SKIN_ICON_TYPE("14"),
        SKIN_SPECIAL("15"),
        ACCOUNT_ID2("16"),
        USER_COINS("17"),
        DIAMONDS("46");

        private final String code;

        ScoreElement(String code) {
            this.code = code;
        }
    }
}
