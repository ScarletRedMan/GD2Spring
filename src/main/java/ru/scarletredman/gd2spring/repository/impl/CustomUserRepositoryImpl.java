package ru.scarletredman.gd2spring.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.dto.UserScoreDTO;
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

    @Override
    public List<UserScoreDTO> getTop100ByStars() {
        var hql = "select new ru.scarletredman.gd2spring.model.dto.UserScoreDTO("
                + "id, username, stars, demons, creatorPoints, coins, userCoins, diamonds, "
                + "skin.icon, skin.firstColor, skin.secondColor, skin.currentIconType, skin.specialSkin"
                + ") from User " + "where banned = false and ratingBanned = false "
                + "order by stars desc "
                + "limit 100";

        var list = entityManager.createQuery(hql, UserScoreDTO.class).getResultList();
        int minStarts = -1;
        int rank = 1;
        for (var dto : list) {
            if (minStarts == -1) minStarts = dto.getStars();

            if (minStarts > dto.getStars()) {
                minStarts = dto.getStars();
                rank++;
            }

            dto.setRank(rank);
        }

        return list;
    }

    @Override
    public List<UserScoreDTO> getTop100ByCreatorPoints() {
        var hql = "select new ru.scarletredman.gd2spring.model.dto.UserScoreDTO("
                + "id, username, stars, demons, creatorPoints, coins, userCoins, diamonds, "
                + "skin.icon, skin.firstColor, skin.secondColor, skin.currentIconType, skin.specialSkin"
                + ") from User " + "where banned = false and ratingBanned = false "
                + "order by creatorPoints desc "
                + "limit 100";

        var result = entityManager.createQuery(hql, UserScoreDTO.class).getResultList();

        int i = 1;
        for (var dto : result) {
            dto.setRank(i++);
        }

        return result;
    }

    @Override
    public int getRating(User user) {
        var hql = "select count(distinct stars) from User where stars > :stars";

        var result = entityManager
                .createQuery(hql, Long.class)
                .setParameter("stars", user.getStars())
                .getSingleResult();
        return result.intValue() + 1;
    }
}
