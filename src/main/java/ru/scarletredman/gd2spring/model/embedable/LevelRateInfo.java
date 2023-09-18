package ru.scarletredman.gd2spring.model.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
public class LevelRateInfo {

    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty = Difficulty.NONE;

    @Column(name = "requested_difficulty", nullable = false)
    private Difficulty requestedDifficulty = Difficulty.NONE;

    @Column(name = "rate_time")
    private Timestamp rateTime = null;

    @Column(name = "length", nullable = false)
    private Length length = Length.TINY;

    @Column(name = "is_featured", nullable = false)
    private boolean featured = false;

    @Column(name = "is_epic", nullable = false)
    private boolean epic = false;

    @Getter
    @RequiredArgsConstructor
    public enum Difficulty {
        NONE(0),
        AUTO(10),
        EASY(20),
        NORMAL(30),
        HARD(40),
        HARDER(50),
        INSANE(60),
        DEMON(70),
        EASY_DEMON(80),
        MEDIUM_DEMON(90),
        HARD_DEMON(100),
        INSANE_DEMON(110),
        EXTREME_DEMON(120);

        private final int code;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Length {
        TINY(0),
        SHORT(1),
        MEDIUM(2),
        LONG(3),
        XL(4);

        private final int code;
    }
}
