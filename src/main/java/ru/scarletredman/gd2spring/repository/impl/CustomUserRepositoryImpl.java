package ru.scarletredman.gd2spring.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.repository.CustomUserRepository;

public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void updateSettingsForUser(User user) {
        var hql = "update User set " + "userSettings.allowFriendRequestsFrom = :friend_req, "
                + "userSettings.allowMessagesFrom = :messages, "
                + "userSettings.showCommentHistoryTo = :show_comments, "
                + "twitchUrl = :twitch, "
                + "twitterUrl = :twitter, "
                + "youtubeUrl = :youtube "
                + "where id = :id";

        var settings = user.getUserSettings();
        entityManager
                .createQuery(hql)
                .setParameter("friend_req", settings.getAllowFriendRequestsFrom())
                .setParameter("messages", settings.getAllowMessagesFrom())
                .setParameter("show_comments", settings.getShowCommentHistoryTo())
                .setParameter("twitch", user.getTwitchUrl())
                .setParameter("twitter", user.getTwitterUrl())
                .setParameter("youtube", user.getYoutubeUrl())
                .setParameter("id", user.getId())
                .executeUpdate();
    }
}
