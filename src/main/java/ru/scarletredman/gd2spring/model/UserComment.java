package ru.scarletredman.gd2spring.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
@Table(name = "user_comments")
public class UserComment {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", updatable = false, nullable = false)
    private User owner;

    @Column(name = "text", nullable = false)
    @Length(max = 140) private String text;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp = Timestamp.from(Instant.now());

    @Column(name = "spam", nullable = false)
    private boolean isSpam = false;

    @Column(name = "likes", nullable = false)
    private int likes = 0;

    public UserComment() {}

    public UserComment(User owner, String text) {
        this.owner = owner;
        this.text = text;
    }
}
