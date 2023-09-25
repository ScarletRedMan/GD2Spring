package ru.scarletredman.gd2spring.model.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
    }
}
