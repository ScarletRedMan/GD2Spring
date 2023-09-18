package ru.scarletredman.gd2spring.model.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LevelData {

    @Column(name = "version", nullable = false)
    private int version = 0;

    @Column(name = "payload", nullable = false)
    private String payload;

    @Column(name = "extra", nullable = false)
    private String extra;

    @Column(name = "info", nullable = false)
    private String info;

    @Column(name = "settings", nullable = false)
    private String settings;

    @Column(name = "wt", nullable = false)
    private int wt = 0;

    @Column(name = "wt2", nullable = false)
    private int wt2 = 0;
}
