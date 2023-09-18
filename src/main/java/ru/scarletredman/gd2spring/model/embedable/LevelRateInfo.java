package ru.scarletredman.gd2spring.model.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class LevelRateInfo {

    @Column(name = "stars", nullable = false)
    private int stars = 0;

    @Column(name = "requested_stars", nullable = false)
    private int requestedStars = 0;

    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty = Difficulty.NONE;

    @Column(name = "rate_time")
    private Timestamp rateTime = null;

    @Column(name = "is_featured", nullable = false)
    private boolean featured = false;

    @Column(name = "is_epic", nullable = false)
    private boolean epic = false;

    @Getter
    @RequiredArgsConstructor
    public enum Difficulty {
        NONE,
        AUTO,
        EASY,
        NORMAL,
        HARD,
        HARDER,
        INSANE,
        DEMON(true),
        EASY_DEMON(true),
        MEDIUM_DEMON(true),
        HARD_DEMON(true),
        INSANE_DEMON(true),
        EXTREME_DEMON(true);

        private final boolean demon;

        Difficulty() {
            this(false);
        }
    }
}
