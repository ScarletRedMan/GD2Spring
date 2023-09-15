package ru.scarletredman.gd2spring.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.dto.UserScoreDTO;
import ru.scarletredman.gd2spring.repository.CustomUserRepository;
import ru.scarletredman.gd2spring.service.type.FindUserPage;

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
                + ") from User where banned = false and ratingBanned = false "
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
                + ") from User where banned = false and ratingBanned = false and creatorPoints > 0 "
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
    public List<UserScoreDTO> getRelativeTop(User user) {
        var hqlBefore = "select new ru.scarletredman.gd2spring.model.dto.UserScoreDTO( "
                + "id, username, stars, demons, creatorPoints, coins, userCoins, diamonds, "
                + "skin.icon, skin.firstColor, skin.secondColor, skin.currentIconType, skin.specialSkin "
                + ") from User where stars > :stars and banned = false and ratingBanned = false "
                + "order by stars asc "
                + "limit 25";

        var hqlUser = "select new ru.scarletredman.gd2spring.model.dto.UserScoreDTO( "
                + "id, username, stars, demons, creatorPoints, coins, userCoins, diamonds, "
                + "skin.icon, skin.firstColor, skin.secondColor, skin.currentIconType, skin.specialSkin "
                + ") from User where id = :id";

        var hqlAfter = "select new ru.scarletredman.gd2spring.model.dto.UserScoreDTO( "
                + "id, username, stars, demons, creatorPoints, coins, userCoins, diamonds, "
                + "skin.icon, skin.firstColor, skin.secondColor, skin.currentIconType, skin.specialSkin "
                + ") from User where stars <= :stars and banned = false and ratingBanned = false and id != :id "
                + "order by stars desc "
                + "limit 24";

        var before = entityManager
                .createQuery(hqlBefore, UserScoreDTO.class)
                .setParameter("stars", user.getStars())
                .getResultList();
        var currentUser = entityManager
                .createQuery(hqlUser, UserScoreDTO.class)
                .setParameter("id", user.getId())
                .getResultList();
        var after = entityManager
                .createQuery(hqlAfter, UserScoreDTO.class)
                .setParameter("stars", user.getStars())
                .setParameter("id", user.getId())
                .getResultList();

        Collections.reverse(before);

        var list = new ArrayList<UserScoreDTO>();
        list.addAll(before);
        list.addAll(currentUser);
        list.addAll(after);
        return list;
    }

    @Override
    public int getRating(int stars) {
        var hql = "select count(distinct stars) from User where stars > :stars";

        var result = entityManager
                .createQuery(hql, Long.class)
                .setParameter("stars", stars)
                .getSingleResult();
        return result.intValue() + 1;
    }

    @Override
    public int getRating(User user) {
        return getRating(user.getStars());
    }

    @Override
    public FindUserPage findUser(String request, int page) {
        long userId;
        try {
            userId = Long.parseLong(request);
        } catch (NumberFormatException ex) {
            userId = -1;
        }

        var hqlUsers = "select new ru.scarletredman.gd2spring.model.dto.UserScoreDTO("
                + "id, username, stars, demons, creatorPoints, coins, userCoins, diamonds, "
                + "skin.icon, skin.firstColor, skin.secondColor, skin.currentIconType, skin.specialSkin) "
                + "from User where id = :id or upper(username) like :username";

        var hqlCount = "select count(*) from User where id = :id or upper(username) like :username";

        var usernameLike = "%" + request.toUpperCase() + "%";
        var users = entityManager
                .createQuery(hqlUsers, UserScoreDTO.class)
                .setFirstResult(page * 10)
                .setMaxResults(10)
                .setParameter("id", userId)
                .setParameter("username", usernameLike)
                .getResultList();

        var total = entityManager
                .createQuery(hqlCount, Long.class)
                .setParameter("id", userId)
                .setParameter("username", usernameLike)
                .getSingleResult();

        return new FindUserPage(users, page, total);
    }
}
