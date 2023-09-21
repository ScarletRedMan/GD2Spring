package ru.scarletredman.gd2spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "songs")
public class Song {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "author", nullable = false)
    private int authorId;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "size", nullable = false)
    private int size;

    @Column(name = "url", nullable = false)
    private String downloadUrl;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "is_disabled", nullable = false)
    private boolean disabled = false;

    @Column(name = "levels_count", nullable = false)
    private int levelsCount = 0;

    @Column(name = "re_upload_time", nullable = false)
    private long reUploadTime;

    public Song() {}
}
