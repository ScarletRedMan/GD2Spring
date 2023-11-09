package ru.scarletredman.gd2spring.model.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.LinkedList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LevelFilters {

    @Column(name = "two_players", nullable = false)
    private boolean twoPlayers = false;

    @Column(name = "has_ldm", nullable = false)
    private boolean lowDetailMode = false;

    @Column(name = "length", nullable = false)
    private Length length = Length.TINY;

    @Getter
    @RequiredArgsConstructor
    public enum Length {
        TINY(0),
        SHORT(1),
        MEDIUM(2),
        LONG(3),
        XL(4);

        private final int code;

        public static List<Length> parseGDSearch(String input) {
            var list = new LinkedList<Length>();
            if (input.equals("-")) return list;

            var nums = input.split(",");
            for (var num : nums) {
                try {
                    var len = values()[Integer.parseInt(num)];
                    list.add(len);
                } catch (NumberFormatException | IndexOutOfBoundsException ignore) {
                }
            }

            return list;
        }
    }
}
