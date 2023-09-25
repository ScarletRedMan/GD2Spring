package ru.scarletredman.gd2spring.model.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.sql.Timestamp;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
        NONE(0),
        AUTO(1),
        EASY(2),
        NORMAL(3),
        HARD(4),
        HARDER(5),
        INSANE(6),
        DEMON(7, true),
        EASY_DEMON(8, true),
        MEDIUM_DEMON(9, true),
        HARD_DEMON(10, true),
        INSANE_DEMON(11, true),
        EXTREME_DEMON(12, true);

        private final int id;
        private final boolean demon;

        Difficulty(int id) {
            this(id, false);
        }
    }
}
