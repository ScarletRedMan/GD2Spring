package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;
import ru.scarletredman.gd2spring.model.dto.UserScoreDTO;
import ru.scarletredman.gd2spring.service.type.FindUserPage;
import ru.scarletredman.gd2spring.util.JoinResponseUtil;

@JsonSerialize(using = ResponseSerializer.class)
public final class GetUsersResponse implements ResponseSerializer.Response {

    private static final String SEPARATOR = "|";

    private final boolean error;
    private final List<UserScoreDTO> users;
    private final long remaining;
    private final int page;

    private GetUsersResponse() {
        error = true;
        users = new ArrayList<>();
        remaining = 0;
        page = 0;
    }

    public GetUsersResponse(FindUserPage page) {
        error = false;
        this.users = page.users();
        this.remaining = page.total();
        this.page = page.page();
    }

    public static GetUsersResponse errorResponse() {
        return new GetUsersResponse();
    }

    @Override
    public String getResponse() {
        if (error || users.isEmpty()) return "-1";

        var chains = new String[users.size()];
        int i = 0;
        for (var user : users) {
            chains[i++] = new UserEncoder(user).toString();
        }

        return String.join(SEPARATOR, chains) + "#" + remaining + ":" + page + ":10";
    }

    @Getter
    public static final class UserEncoder {

        private final Map<Element, Object> elements = new EnumMap<>(Element.class);

        public UserEncoder(UserScoreDTO dto) {
            init(dto);
        }

        private void init(UserScoreDTO dto) {
            elements.put(Element.NAME, dto.getUsername());
            elements.put(Element.PLAYER_ID, dto.getId());
            elements.put(Element.STARS, dto.getStars());
            elements.put(Element.DEMONS, dto.getDemons());
            elements.put(Element.ACCOUNT_ID1, dto.getId());
            elements.put(Element.ACCOUNT_ID2, dto.getId());
            elements.put(Element.CREATOR_POINTS, dto.getCreatorPoints());
            elements.put(Element.SKIN_ICON, dto.getSkinIcon());
            elements.put(Element.SKIN_FIRST_COLOR, dto.getSkinFirstColor());
            elements.put(Element.SKIN_SECOND_COLOR, dto.getSkinSecondColor());
            elements.put(Element.COINS, dto.getCoins());
            elements.put(Element.SKIN_ICON_TYPE, dto.getSkinIconType());
            elements.put(Element.SKIN_SPECIAL, dto.getSkinSpecial());
            elements.put(Element.USER_COINS, dto.getUserCoins());
            elements.put(Element.DIAMONDS, dto.getDiamonds());
        }

        @Override
        public String toString() {
            return JoinResponseUtil.join(elements, ":");
        }

        @Getter
        public enum Element implements JoinResponseUtil.Key {
            NAME("1"),
            PLAYER_ID("2"),
            STARS("3"),
            DEMONS("4"),
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

            Element(String code) {
                this.code = code;
            }
        }
    }
}
