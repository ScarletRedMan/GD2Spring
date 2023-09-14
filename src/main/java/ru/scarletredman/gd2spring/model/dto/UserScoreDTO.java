package ru.scarletredman.gd2spring.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.scarletredman.gd2spring.model.User;

@Getter
@Setter
@RequiredArgsConstructor
public class UserScoreDTO {

    private final long id;
    private final String username;
    private final int stars;
    private final int demons;
    private final int creatorPoints;
    private final int coins;
    private final int userCoins;
    private final int diamonds;
    private final int skinIcon;
    private final int skinFirstColor;
    private final int skinSecondColor;
    private final int skinIconType;
    private final int skinSpecial;

    private transient int rank = 0;

    public UserScoreDTO(User user) {
        id = user.getId();
        username = user.getUsername();
        stars = user.getStars();
        demons = user.getDemons();
        creatorPoints = user.getCreatorPoints();
        coins = user.getCoins();
        userCoins = user.getUserCoins();
        diamonds = user.getDiamonds();

        var skin = user.getSkin();
        skinIcon = skin.getIcon();
        skinFirstColor = skin.getFirstColor();
        skinSecondColor = skin.getSecondColor();
        skinIconType = skin.getCurrentIconType();
        skinSpecial = skin.getSpecialSkin();
    }
}
