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

    @Column(name = "coins", nullable = false)
    private int coins = 0;

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
        NONE(0, "N/A", 0),
        AUTO(1, "Auto", 50),
        EASY(2, "Easy", 10),
        NORMAL(3, "Normal", 20),
        HARD(4, "Hard", 30),
        HARDER(5, "Harder", 40),
        INSANE(6, "Insane", 50),
        DEMON(7, 0, "Demon"),
        EASY_DEMON(8, 1, "Easy demon"),
        MEDIUM_DEMON(9, 2, "Medium demon"),
        HARD_DEMON(10, 3, "Hard demon"),
        INSANE_DEMON(11, 4, "Insane demon"),
        EXTREME_DEMON(12, 5, "Extreme demon");

        private final int id;
        private final String shortName;
        private final String fullName;
        private final boolean demon;
        private final int gdDiff;
        private final int demonDiff;

        Difficulty(int id, String shortName, int gdDiff) {
            this(id, shortName, shortName, false, gdDiff, 0);
        }

        Difficulty(int id, int demonDiff, String fullName) {
            this(id, "Demon", fullName, true, 50, demonDiff);
        }

        public boolean isAuto() {
            return this == AUTO;
        }
    }
}
