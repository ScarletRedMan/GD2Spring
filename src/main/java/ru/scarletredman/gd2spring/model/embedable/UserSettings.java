package ru.scarletredman.gd2spring.model.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class UserSettings {

    @Column(name = "s_allow_messages", nullable = false)
    @Size(max = 2)
    private AllowMessagesFrom allowMessagesFrom = AllowMessagesFrom.ALL;

    @Column(name = "s_allow_friend_req")
    @Size(max = 1)
    private AllowFriendRequestsFrom allowFriendRequestsFrom = AllowFriendRequestsFrom.ALL;

    @Column(name = "s_comment_history")
    @Size(max = 2)
    private ShowCommentHistoryTo showCommentHistoryTo = ShowCommentHistoryTo.ALL;

    @Getter
    public enum AllowMessagesFrom {
        ALL(0),
        FRIENDS(1),
        NONE(2);

        final int value;

        AllowMessagesFrom(int value) {
            this.value = value;
        }

        public static AllowMessagesFrom fromValue(int value) {
            return switch (value) {
                case 0 -> ALL;
                case 1 -> FRIENDS;
                case 2 -> NONE;
                default -> throw new IllegalArgumentException(String.valueOf(value));
            };
        }
    }

    @Getter
    public enum AllowFriendRequestsFrom {
        ALL(0),
        NONE(1);

        final int value;

        AllowFriendRequestsFrom(int value) {
            this.value = value;
        }

        public static AllowFriendRequestsFrom fromValue(int value) {
            return switch (value) {
                case 0 -> ALL;
                case 1 -> NONE;
                default -> throw new IllegalArgumentException(String.valueOf(value));
            };
        }
    }

    @Getter
    public enum ShowCommentHistoryTo {
        ALL(0),
        FRIENDS(1),
        ME(2);

        final int value;

        ShowCommentHistoryTo(int value) {
            this.value = value;
        }

        public static ShowCommentHistoryTo fromValue(int value) {
            return switch (value) {
                case 0 -> ALL;
                case 1 -> FRIENDS;
                case 2 -> ME;
                default -> throw new IllegalArgumentException(String.valueOf(value));
            };
        }
    }
}
