package ru.scarletredman.gd2spring.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.scarletredman.gd2spring.model.Song;
import ru.scarletredman.gd2spring.model.embedable.LevelFilters;
import ru.scarletredman.gd2spring.model.embedable.LevelRateInfo;

@Getter
@Setter
@RequiredArgsConstructor
public class GDLevelDTO {

    private final long id;
    private final String name;
    private final String description;
    private final int version;
    private final User user;
    private final LevelFilters filters;
    private final LevelRateInfo rate;
    private final int downloads;
    private final int audioTrack;
    private final int likes;
    private final Long originalLevel;
    private Song song = null;

    public record User(long userId, String username) {}
}
