package ru.scarletredman.gd2spring.service.type;

import java.util.List;
import ru.scarletredman.gd2spring.model.Level;
import ru.scarletredman.gd2spring.model.embedable.LevelFilters;
import ru.scarletredman.gd2spring.model.embedable.LevelRateInfo;

public record LevelListPage(List<Level> levels, int total, int offset) {

    public record Filters(
            String name,
            LevelRateInfo.Difficulty difficulty,
            LevelFilters.Length length,
            int page,
            boolean uncompleted,
            boolean onlyUncompleted,
            boolean featured,
            boolean original,
            boolean forTwoPlayers,
            boolean coins,
            boolean epic,
            boolean noStar,
            int demonFilter,
            int song,
            int customSong) {}
}
