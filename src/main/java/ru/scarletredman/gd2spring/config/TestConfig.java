package ru.scarletredman.gd2spring.config;

import java.sql.Timestamp;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.scarletredman.gd2spring.model.Level;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.UserComment;
import ru.scarletredman.gd2spring.model.embedable.LevelRateInfo;
import ru.scarletredman.gd2spring.service.LevelService;
import ru.scarletredman.gd2spring.service.MessageService;
import ru.scarletredman.gd2spring.service.UserCommentService;
import ru.scarletredman.gd2spring.service.UserService;

@Profile("test")
@Configuration
@RequiredArgsConstructor
public class TestConfig {

    private final UserService userService;
    private final UserCommentService userCommentService;
    private final LevelService levelService;
    private final MessageService messageService;

    @Autowired
    void createTestUser(boolean debugMode) {
        if (!debugMode) return;

        var user = userService.registerUser("test", "qwerty", "m@m.m");
        for (int i = 0; i < 30; i++) {
            userCommentService.writeComment(new UserComment(user, i + ") Hello world!"));
        }

        for (int i = 0; i < 10; i++) {
            var u = userService.registerUser("test" + i, "qwerty", "m" + i + "@m.m");
            u.getSkin().setFirstColor(i * 2 + 1);
            u.getSkin().setSecondColor(i * 2 + 7);
            u.getSkin().setIcon(i + 1);
            u.getSkin().setAccIcon(i + 1);
            u.setStars(i * 2);
            u.setCreatorPoints(i);
            userService.updateScore(u);

            userCommentService.writeComment(new UserComment(u, "Hello world!!!"));
        }

        for (int i = 0; i < 30; i++) {
            var level = createTestLevel(user, "Test level " + i, i, i * 2, i % 4, i % 3 == 0);
            levelService.uploadLevel(level);
        }

        var user2 = userService.findUserById(2).get();
        for (int i = 0; i < 30; i++) {
            messageService.sendMessage(user, user2, "Sent " + i, "Hello my dear friend, Test2!");
            messageService.sendMessage(user2, user, "Received " + i, "Hello my dear friend, Test!");
        }
    }

    Level createTestLevel(User owner, String name, int likes, int downloads, int stars, boolean featured) {
        var level = new Level(owner, name);
        level.setDescription("Hello world!");
        level.setObjects(4);

        level.setLikes(likes);
        level.setDownloads(downloads);

        var data = level.getData();
        data.setExtra(
                "0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0");
        data.setInfo("H4sIAAAAAAAACzPQMzO1NrQ20LMwtrY21DM1sbYGAPBOeXwTAAAA");
        data.setPayload(
                "H4sIAAAAAAAAC6WQ0Q3DIAxEF3Iln8GEqF-ZIQPcAFmhwxdw8lMlaqr-3HEHfrLY1lQFzEojzJlo7gTCLCzKzAdYCFXlRBDepVJZiRc4EGr3EPgfMZ8i-psYuAUx9vkzUP-NA6TfMH6J0V-2KRcY2RYk0W4eVsKyNI3zFM1utdua5pFsaADGxZKHxi00DKJPCMSkLSVJ4EesEcVaVs17nXBe54_6DWF4ex1jAgAA");

        var rate = level.getRate();
        rate.setRequestedStars(10);
        rate.setStars(stars);
        rate.setDifficulty(LevelRateInfo.Difficulty.values()[stars]);
        rate.setFeatured(featured);
        rate.setEpic(featured);
        if (stars != 0 || featured) {
            rate.setRateTime(Timestamp.from(Instant.now()));
        }

        return level;
    }
}
