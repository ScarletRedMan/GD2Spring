package ru.scarletredman.gd2spring.service.type;

import java.util.List;
import ru.scarletredman.gd2spring.model.dto.GDLevelDTO;
import ru.scarletredman.gd2spring.model.embedable.LevelFilters;
import ru.scarletredman.gd2spring.model.embedable.LevelRateInfo;

public record LevelListPage(List<GDLevelDTO> levels, long total, int offset) {

    public record Filters(
            String name,
            List<LevelRateInfo.Difficulty> difficulty,
            List<LevelFilters.Length> length,
            int page,
            boolean onlyCompleted,
            boolean onlyUncompleted,
            boolean featured,
            boolean original,
            boolean forTwoPlayers,
            boolean coins,
            boolean epic,
            boolean noStar,
            int song,
            int customSong) {}
}
