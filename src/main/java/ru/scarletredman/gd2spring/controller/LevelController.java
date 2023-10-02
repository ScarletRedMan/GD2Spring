package ru.scarletredman.gd2spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.scarletredman.gd2spring.controller.annotation.GeometryDashAPI;
import ru.scarletredman.gd2spring.controller.response.GetLevelsResponse;
import ru.scarletredman.gd2spring.model.embedable.LevelFilters;
import ru.scarletredman.gd2spring.model.embedable.LevelRateInfo;
import ru.scarletredman.gd2spring.security.annotation.GDAuthorizedOnly;
import ru.scarletredman.gd2spring.service.LevelService;
import ru.scarletredman.gd2spring.service.type.LevelListPage;
import ru.scarletredman.gd2spring.util.ResponseLogger;

@GeometryDashAPI
@RestController
@RequiredArgsConstructor
public class LevelController {

    private final ResponseLogger responseLogger;
    private final LevelService levelService;

    @GDAuthorizedOnly
    @PostMapping("/uploadGJLevel21.php")
    String uploadLevel(
            @RequestParam(name = "levelID") long levelId,
            @RequestParam(name = "levelName") String name,
            @RequestParam(name = "levelDesc") String description,
            @RequestParam(name = "levelVersion") int version,
            @RequestParam(name = "levelLength") int length,
            @RequestParam(name = "audioTrack") int soundtrack,
            @RequestParam(name = "auto") int isAuto,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "original") int original,
            @RequestParam(name = "twoPlayer") int isForTwoPlayers,
            @RequestParam(name = "songID") int songId,
            @RequestParam(name = "objects") int objects,
            @RequestParam(name = "coins") int coins,
            @RequestParam(name = "requestedStars") int requestedStars,
            @RequestParam(name = "unlisted") int isUnlisted,
            @RequestParam(name = "wt") int wt,
            @RequestParam(name = "wt2") int wt2,
            @RequestParam(name = "ldm") int lowDetailMode,
            @RequestParam(name = "extraString") String extraString,
            @RequestParam(name = "seed") String seed,
            @RequestParam(name = "seed2") String seed2,
            @RequestParam(name = "levelString") String levelString,
            @RequestParam(name = "levelInfo") String levelInfo) {
        return "-1";
    }

    @PostMapping("/getGJLevels21.php")
    GetLevelsResponse getLevels(
            @RequestParam(name = "type") int type,
            @RequestParam(name = "str") String levelName,
            @RequestParam(name = "diff") String difficulty, // "-" or numbers
            @RequestParam(name = "len") String length, // "-" or numbers
            @RequestParam(name = "page") int page,
            @RequestParam(name = "total") int total,
            @RequestParam(name = "uncompleted", required = false, defaultValue = "0") int isOnlyUncompleted,
            @RequestParam(name = "onlyCompleted", required = false, defaultValue = "0") int isOnlyCompleted,
            @RequestParam(name = "featured", required = false, defaultValue = "0") int isFeatured,
            @RequestParam(name = "original", required = false, defaultValue = "0") int isOriginal,
            @RequestParam(name = "twoPlayer", required = false, defaultValue = "0") int isForTwoPlayers,
            @RequestParam(name = "coins", required = false, defaultValue = "0") int hasCoins,
            @RequestParam(name = "epic", required = false, defaultValue = "0") int isEpic,
            @RequestParam(name = "noStar", required = false, defaultValue = "0") int noStar,
            @RequestParam(name = "demonFilter", required = false, defaultValue = "0") int demonFilter,
            @RequestParam(name = "song", required = false, defaultValue = "0") int song,
            @RequestParam(name = "customSong", required = false, defaultValue = "0") int customSong) {

        var filters = new LevelListPage.Filters(
                levelName,
                LevelRateInfo.Difficulty.parseGDSearch(difficulty, demonFilter),
                LevelFilters.Length.parseGDSearch(length),
                page,
                isOnlyCompleted == 1,
                isOnlyUncompleted == 1,
                isFeatured == 1,
                isOriginal == 1,
                isForTwoPlayers == 1,
                hasCoins == 1,
                isEpic == 1,
                noStar == 1,
                song,
                customSong);

        var levels = levelService.getLevels(filters);
        return responseLogger.result(new GetLevelsResponse(levels));
    }

    @GDAuthorizedOnly
    @PostMapping("/getGJDailyLevel.php")
    String getDailyLevel(@RequestParam(name = "weekly") int isWeekly) {
        return "-1";
    }
}
