package ru.scarletredman.gd2spring.model.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LevelFilters {

    @Column(name = "two_players", nullable = false)
    private boolean twoPlayers = false;

    @Column(name = "has_ldm", nullable = false)
    private boolean lowDetailMode = false;
}
