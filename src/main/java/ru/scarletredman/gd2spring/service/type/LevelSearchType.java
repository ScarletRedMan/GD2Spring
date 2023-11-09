package ru.scarletredman.gd2spring.service.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LevelSearchType {
    SEARCH_BY_NAME(0),
    MOST_DOWNLOADS(1),
    MOST_LIKED(2),
    TRENDING(3),
    FEATURED(6),
    MAGIC(7),
    MAP_PACK(10),
    AWARDED(11),
    FOLLOWED(12),
    FRIENDS(13),
    HALL_OF_FAME(16),
    DAILY_SAFE(21),
    WEEKLY_SAFE(22),
    EVENT_SAFE(23),
    ;

    private final int code;

    public static LevelSearchType find(int code) {
        for (var type : values()) {
            if (code == type.code) return type;
        }
        return MOST_LIKED;
    }
}
