package ru.scarletredman.gd2spring.service.type;

import java.util.List;
import org.springframework.lang.Nullable;
import ru.scarletredman.gd2spring.model.dto.GDLevelDTO;
import ru.scarletredman.gd2spring.model.embedable.LevelFilters;
import ru.scarletredman.gd2spring.model.embedable.LevelRateInfo;

public record LevelListPage(List<GDLevelDTO> levels, int total, int offset) {

    public record Filters(
            String name,
            @Nullable LevelRateInfo.Difficulty difficulty,
            @Nullable LevelFilters.Length length,
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
