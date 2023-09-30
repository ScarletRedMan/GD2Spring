package ru.scarletredman.gd2spring.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import ru.scarletredman.gd2spring.model.Level;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.dto.GDLevelDTO;
import ru.scarletredman.gd2spring.model.embedable.LevelFilters;
import ru.scarletredman.gd2spring.model.embedable.LevelRateInfo;
import ru.scarletredman.gd2spring.repository.CustomLevelRepository;
import ru.scarletredman.gd2spring.service.type.LevelListPage;

public class CustomLevelRepositoryImpl implements CustomLevelRepository {

    private static final String LEVEL_NAME_REGEX = "^[aA-zZ\\d\\s]+$";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public LevelListPage getLevels(LevelListPage.Filters filters) {
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
        var query = criteria.createQuery(GDLevelDTO.class);
        var rootLevel = query.from(Level.class);
        var joinUser = rootLevel.<Level, User>join("owner", JoinType.INNER);

        query.select(createLevelDTO(criteria, rootLevel, joinUser));

        var criteriaFilters = new ArrayList<Predicate>();
        if (searchLevelName != null) {
            criteriaFilters.add(criteria.like(rootLevel.get("name"), searchLevelName + "%"));
        }
        // todo: implement filters

        query.where(criteriaFilters.toArray(new Predicate[0]));

        var levels = entityManager.createQuery(query).getResultList();

        var total = 0;

        return new LevelListPage(levels, total, filters.page());
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
                rootLevel.get("original").get("id"),
                rootLevel.get("objects"));
    }
}
