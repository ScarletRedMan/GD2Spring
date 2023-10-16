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
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "sender", nullable = false)
    private User sender;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver", nullable = false)
    private User receiver;

    @Length(max = 35) @Column(name = "subject", nullable = false)
    private String subject;

    @Length(max = 200) @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "time", nullable = false)
    private Timestamp time = Timestamp.from(Instant.now());

    @Column(name = "is_new")
    private boolean isNew = true;

    public Message() {}

    public Message(User sender, User receiver, String subject, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.text = text;
    }
}
