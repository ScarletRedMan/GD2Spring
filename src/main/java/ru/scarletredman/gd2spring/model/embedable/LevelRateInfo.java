package ru.scarletredman.gd2spring.model.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
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

    @Column(name = "verified_coins", nullable = false)
    private boolean verifiedCoins = false;

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
        EASY_DEMON(8, 3, "Easy demon"),
        MEDIUM_DEMON(9, 4, "Medium demon"),
        HARD_DEMON(10, 0, "Hard demon"),
        INSANE_DEMON(11, 5, "Insane demon"),
        EXTREME_DEMON(12, 6, "Extreme demon");

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

        public static List<Difficulty> parseGDSearch(String input, int demonFilter) {
            var list = new LinkedList<Difficulty>();
            if (input.equals("-")) return list;

            var nums = input.split(",");
            for (var num : nums) {
                int diff;
                try {
                    diff = Integer.parseInt(num);
                } catch (NumberFormatException ignore) {
                    continue;
                }

                switch (diff) {
                    case -1 -> list.add(NONE);
                    case 1 -> list.add(EASY);
                    case 2 -> list.add(NORMAL);
                    case 3 -> list.add(HARD);
                    case 4 -> list.add(HARDER);
                    case 5 -> list.add(INSANE);
                    case -3 -> list.add(AUTO);
                    case -2 -> {
                        switch (demonFilter) {
                            case 0 -> list.add(DEMON);
                            case 1 -> list.add(EASY_DEMON);
                            case 2 -> list.add(MEDIUM_DEMON);
                            case 3 -> list.add(HARD_DEMON);
                            case 4 -> list.add(INSANE_DEMON);
                            case 5 -> list.add(EXTREME_DEMON);
                        }
                    }
                }
            }

            return list;
        }
    }
}
