package ru.scarletredman.gd2spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.scarletredman.gd2spring.model.embedable.LevelData;
import ru.scarletredman.gd2spring.model.embedable.LevelFilters;
import ru.scarletredman.gd2spring.model.embedable.LevelRateInfo;

@Getter
@Setter
@Entity
@Table(
        name = "levels",
        indexes = {@Index(name = "level_name_index", columnList = "name")})
public class Level {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @Column(name = "original")
    private Long original = null;

    @Embedded
    private LevelRateInfo rate = new LevelRateInfo();

    @Embedded
    private LevelData data = new LevelData();

    @Embedded
    private LevelFilters filters = new LevelFilters();

    @Column(name = "password", nullable = false)
    private int password = 0;

    @Column(name = "unlisted", nullable = false)
    private boolean unlisted = false;

    @Column(name = "likes", nullable = false)
    private int likes = 0;

    @Column(name = "downloads", nullable = false)
    private int downloads = 0;

    @Column(name = "sound_track", nullable = false)
    private int soundTrack = 0;

    @Column(name = "song_id", nullable = false)
    private int songId = 0;

    @Column(name = "objects", nullable = false)
    private int objects = 0;

    public Level() {}

    public Level(User owner, String name) {
        this.owner = owner;
        this.name = name;
    }
}
