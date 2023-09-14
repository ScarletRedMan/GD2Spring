package ru.scarletredman.gd2spring.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.scarletredman.gd2spring.controller.annotation.GeometryDashAPI;
import ru.scarletredman.gd2spring.controller.response.ScoresResponse;
import ru.scarletredman.gd2spring.controller.response.UserInfoResponse;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.dto.UserScoreDTO;
import ru.scarletredman.gd2spring.security.annotation.GDAuthorizedOnly;
import ru.scarletredman.gd2spring.service.UserService;
import ru.scarletredman.gd2spring.util.ResponseLogger;

@RestController
@GeometryDashAPI
@RequiredArgsConstructor
public class UserScoreController {

    private final UserService userService;
    private final ResponseLogger responseLogger;

    @GDAuthorizedOnly
    @PostMapping("/updateGJUserScore22.php")
    int updateScore(
            @RequestParam(name = "userName") String username,
            @RequestParam(name = "stars") int stars,
            @RequestParam(name = "demons") int demons,
            @RequestParam(name = "diamonds") int diamonds,
            @RequestParam(name = "coins") int coins,
            @RequestParam(name = "userCoins") int userCoins,
            @RequestParam(name = "secret") String secret,
            @RequestParam(name = "icon") int icon,
            @RequestParam(name = "color1") int firstColor,
            @RequestParam(name = "color2") int secondColor,
            @RequestParam(name = "iconType") int iconType,
            @RequestParam(name = "special") int special,
            @RequestParam(name = "accIcon") int accIcon,
            @RequestParam(name = "accShip") int accShip,
            @RequestParam(name = "accBall") int accBall,
            @RequestParam(name = "accBird") int accBird,
            @RequestParam(name = "accDart") int accDart,
            @RequestParam(name = "accRobot") int accRobot,
            @RequestParam(name = "accGlow") int accGlow,
            @RequestParam(name = "accSpider") int accSpider,
            @RequestParam(name = "accExplosion") int accExplosion) {

        var user = UserService.getCurrentUserFromSecurityContextHolder();
        user.setStars(stars);
        user.setDemons(demons);
        user.setDiamonds(diamonds);
        user.setCoins(coins);
        user.setUserCoins(userCoins);

        var skin = user.getSkin();
        skin.setIcon(icon);
        skin.setFirstColor(firstColor);
        skin.setSecondColor(secondColor);
        skin.setCurrentIconType(iconType);
        skin.setSpecialSkin(special);
        skin.setAccIcon(accIcon);
        skin.setAccShip(accShip);
        skin.setAccBall(accBall);
        skin.setAccBird(accBird);
        skin.setAccDart(accDart);
        skin.setAccRobot(accRobot);
        skin.setAccGlow(accGlow);
        skin.setAccSpider(accSpider);
        skin.setAccExplosion(accExplosion);

        userService.updateScore(user);
        return responseLogger.result(1);
    }

    @GDAuthorizedOnly
    @PostMapping("/getGJUserInfo20.php")
    UserInfoResponse getUserInfo(@RequestParam(name = "targetAccountID") int targetAccountId) {
        var user = UserService.getCurrentUserFromSecurityContextHolder();
        User targetUser;

        {
            var temp = userService.findUserWithRating(targetAccountId);
            if (temp.isEmpty()) return UserInfoResponse.errorResponse();
            targetUser = temp.get();
        }

        return responseLogger.result(new UserInfoResponse(targetUser, user.equals(targetUser)));
    }

    @GDAuthorizedOnly
    @PostMapping("/getGJScores20.php")
    ScoresResponse getScore(
            @RequestParam(name = "type") String type,
            @RequestParam(name = "count") int count,
            @RequestParam(name = "secret") String secret) {

        var user = UserService.getCurrentUserFromSecurityContextHolder();
        List<UserScoreDTO> players = switch (type) { // todo: friends
                    case "top" -> userService.getTop100UsersByStars();
                    case "creators" -> userService.getTop100UsersByCreatorPoints();
                    case "relative" -> userService.getRelativeTop(user);
                    default -> List.of(new UserScoreDTO(user));
                };

        return responseLogger.result(new ScoresResponse(players));
    }
}
