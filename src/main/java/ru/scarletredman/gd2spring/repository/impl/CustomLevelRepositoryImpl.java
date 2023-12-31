package ru.scarletredman.gd2spring.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.lang.Nullable;
import ru.scarletredman.gd2spring.model.Level;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.dto.GDLevelDTO;
import ru.scarletredman.gd2spring.model.embedable.LevelFilters;
import ru.scarletredman.gd2spring.model.embedable.LevelRateInfo;
import ru.scarletredman.gd2spring.repository.CustomLevelRepository;
import ru.scarletredman.gd2spring.service.type.LevelListPage;
import ru.scarletredman.gd2spring.service.type.LevelSearchType;

public class CustomLevelRepositoryImpl implements CustomLevelRepository {

    private static final String LEVEL_NAME_REGEX = "^[aA-zZ\\d\\s]+$";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public LevelListPage getLevels(LevelListPage.Filters filters) {
        if (checkControversies(filters)) {
            return new LevelListPage(new ArrayList<>(), 0, 0);
        }

        final String searchLevelName;
        {
            var temp = filters.name().trim();
            if (temp.isEmpty()) {
                searchLevelName = null;
            } else {
                if (!temp.matches(LEVEL_NAME_REGEX)) {
                    return new LevelListPage(new ArrayList<>(), 0, 0);
                }
                searchLevelName = temp;
            }
        }

        var criteria = entityManager.getCriteriaBuilder();

        var selectById = selectById(searchLevelName, criteria);
        var selectQuery = selectLevelsQuery(criteria, searchLevelName, filters);
        var levels = new LinkedList<GDLevelDTO>();
        if (selectById != null) levels.add(selectById);
        levels.addAll(entityManager
                .createQuery(selectQuery)
                .setFirstResult(10 * filters.page())
                .setMaxResults(10)
                .getResultList());

        var total = entityManager
                .createQuery(countLevelsQuery(criteria, searchLevelName, filters))
                .getSingleResult();

        return new LevelListPage(levels, total, filters.page());
    }

    private boolean checkControversies(LevelListPage.Filters filters) {
        if (filters.page() < 0) return true;
        if (filters.onlyCompleted() && filters.onlyUncompleted()) return true;
        if ((filters.featured() || filters.epic()) && filters.noStar()) return true;
        if (filters.song() < -1) return true;
        if (filters.hasStar() && filters.noStar()) return true;
        return false;
    }

    private @Nullable GDLevelDTO selectById(@Nullable String input, CriteriaBuilder criteria) {
        if (input == null) return null;

        long levelId;
        try {
            levelId = Long.parseLong(input);
        } catch (NumberFormatException ex) {
            return null;
        }

        var query = criteria.createQuery(GDLevelDTO.class);
        var rootLevel = query.from(Level.class);
        var joinUser = rootLevel.<Level, User>join("owner", JoinType.INNER);

        query.select(createLevelDTO(criteria, rootLevel, joinUser));
        query.where(criteria.equal(rootLevel.get("id"), levelId));

        try {
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private CriteriaQuery<GDLevelDTO> selectLevelsQuery(
            CriteriaBuilder criteria, @Nullable String levelName, LevelListPage.Filters filters) {
        var query = criteria.createQuery(GDLevelDTO.class);
        var rootLevel = query.from(Level.class);
        var joinUser = rootLevel.<Level, User>join("owner", JoinType.INNER);

        query.select(createLevelDTO(criteria, rootLevel, joinUser));
        query.where(applyFilters(criteria, rootLevel, levelName, filters));
        // query.orderBy(applySorting(criteria, rootLevel, filters.type())); // TODO: FIX IT
        return query;
    }

    private CriteriaQuery<Long> countLevelsQuery(
            CriteriaBuilder criteria, @Nullable String levelName, LevelListPage.Filters filters) {

        var query = criteria.createQuery(Long.class);
        var rootLevel = query.from(Level.class);

        query.select(criteria.count(rootLevel));
        query.where(applyFilters(criteria, rootLevel, levelName, filters));
        return query;
    }

    private List<Order> applySorting(CriteriaBuilder criteria, Root<Level> rootLevel, LevelSearchType type) {
        return switch (type) {
            case SEARCH_BY_NAME -> List.of(criteria.asc(rootLevel.get("name")));
            case MOST_DOWNLOADS -> List.of(criteria.desc(rootLevel.get("downloads")));
            case FEATURED, AWARDED, HALL_OF_FAME -> List.of(
                    criteria.desc(rootLevel.get("rate").get("rateTime")));
            case MAGIC -> List.of(criteria.desc(rootLevel.get("id")));
            default -> List.of(criteria.desc(rootLevel.get("likes")));
        };
    }

    private Predicate[] applyFilters(
            CriteriaBuilder criteria,
            Root<Level> rootLevel,
            @Nullable String levelName,
            LevelListPage.Filters filters) {

        var criteriaFilters = new ArrayList<Predicate>();
        criteriaFilters.add(criteria.isFalse(rootLevel.get("unlisted")));
        if (levelName != null) {
            criteriaFilters.add(criteria.like(criteria.lower(rootLevel.get("name")), levelName.toLowerCase() + "%"));
        }
        if (!filters.difficulty().isEmpty()) {
            criteriaFilters.add(rootLevel.get("rate").get("difficulty").in(filters.difficulty()));
        }
        if (!filters.length().isEmpty()) {
            criteriaFilters.add(rootLevel.get("filters").get("length").in(filters.length()));
        }
        // todo: completed and uncompleted (need player leaderboards)
        if (filters.featured()) {
            criteriaFilters.add(criteria.isTrue(rootLevel.get("rate").get("featured")));
        }
        if (filters.original()) {
            criteriaFilters.add(rootLevel.get("original").isNull());
        }
        if (filters.forTwoPlayers()) {
            criteriaFilters.add(criteria.isTrue(rootLevel.get("filters").get("twoPlayers")));
        }
        if (filters.coins()) {
            criteriaFilters.add(criteria.gt(rootLevel.get("rate").get("coins"), 0));
        }
        if (filters.epic()) {
            criteriaFilters.add(criteria.isTrue(rootLevel.get("rate").get("epic")));
        }
        if (filters.noStar()) {
            criteriaFilters.add(criteria.equal(rootLevel.get("rate").get("stars"), 0));
        }
        if (filters.hasStar()) {
            criteriaFilters.add(criteria.gt(rootLevel.get("rate").get("stars"), 0));
        }
        if (filters.song() != -1 && !filters.customSong()) {
            criteriaFilters.add(criteria.equal(rootLevel.get("soundTrack"), filters.song()));
        }
        if (filters.song() != -1 && filters.customSong()) {
            criteriaFilters.add(criteria.equal(rootLevel.get("songId"), filters.song()));
        }
        if (filters.type() == LevelSearchType.AWARDED
                || filters.type() == LevelSearchType.FEATURED
                || filters.type() == LevelSearchType.HALL_OF_FAME) {
            criteriaFilters.add(criteria.isNotNull(rootLevel.get("rate").get("rateTime")));
        }
        if (filters.type() == LevelSearchType.AWARDED) {
            criteriaFilters.add(criteria.gt(rootLevel.get("rate").get("stars"), 0));
        }
        if (filters.type() == LevelSearchType.FEATURED) {
            criteriaFilters.add(criteria.isTrue(rootLevel.get("rate").get("featured")));
        }
        if (filters.type() == LevelSearchType.HALL_OF_FAME) {
            criteriaFilters.add(criteria.isTrue(rootLevel.get("rate").get("epic")));
        }

        return criteriaFilters.toArray(new Predicate[0]);
    }

    private CompoundSelection<GDLevelDTO> createLevelDTO(
            CriteriaBuilder criteria, Root<Level> rootLevel, Join<Level, User> joinUser) {
        return criteria.construct(
                GDLevelDTO.class,
                rootLevel.get("id"),
                rootLevel.get("name"),
                rootLevel.get("description"),
                rootLevel.get("data").get("version"),
                criteria.construct(GDLevelDTO.User.class, joinUser.get("id"), joinUser.get("username")),
                criteria.construct(
                        LevelFilters.class,
                        rootLevel.get("filters").get("twoPlayers"),
                        rootLevel.get("filters").get("lowDetailMode"),
                        rootLevel.get("filters").get("length")),
                criteria.construct(
                        LevelRateInfo.class,
                        rootLevel.get("rate").get("stars"),
                        rootLevel.get("rate").get("requestedStars"),
                        rootLevel.get("rate").get("coins"),
                        rootLevel.get("rate").get("verifiedCoins"),
                        rootLevel.get("rate").get("difficulty"),
                        rootLevel.get("rate").get("rateTime"),
                        rootLevel.get("rate").get("featured"),
                        rootLevel.get("rate").get("epic")),
                rootLevel.get("downloads"),
                rootLevel.get("soundTrack"),
                rootLevel.get("likes"),
                rootLevel.get("original"),
                rootLevel.get("objects"));
    }
}
