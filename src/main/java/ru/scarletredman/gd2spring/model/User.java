package ru.scarletredman.gd2spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import ru.scarletredman.gd2spring.model.embedable.Skin;
import ru.scarletredman.gd2spring.model.embedable.UserSettings;
import ru.scarletredman.gd2spring.security.role.DefaultRoles;
import ru.scarletredman.gd2spring.security.role.Role;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "banned", nullable = false)
    private boolean banned = false;

    @Column(name = "stars", nullable = false)
    @Min(0)
    private int stars = 0;

    @Column(name = "demons", nullable = false)
    @Min(0)
    private int demons = 0;

    @Column(name = "diamonds", nullable = false)
    @Min(0)
    private int diamonds = 0;

    @Column(name = "coins", nullable = false)
    @Min(0)
    private int coins = 0;

    @Column(name = "user_coins", nullable = false)
    @Min(0)
    private int userCoins = 0;

    @Column(name = "creator_points", nullable = false)
    @Min(0)
    private int creatorPoints = 0;

    @Column(name = "rating_banned", nullable = false)
    private boolean ratingBanned = false;

    @Column(name = "twitter_url", nullable = false)
    private String twitterUrl = "";

    @Column(name = "twitch_url", nullable = false)
    private String twitchUrl = "";

    @Column(name = "youtube_url", nullable = false)
    private String youtubeUrl = "";

    @Embedded
    private Skin skin = new Skin();

    @Embedded
    private UserSettings userSettings = new UserSettings();

    private transient int rating = 0;

    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public Set<Role> getAuthorities() {
        return Set.of(DefaultRoles.USER);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBanned();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof User target) {
            return Objects.equals(getId(), target.getId());
        }
        return false;
    }
}
