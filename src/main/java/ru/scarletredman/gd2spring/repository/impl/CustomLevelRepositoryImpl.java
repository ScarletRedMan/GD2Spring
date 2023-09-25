package ru.scarletredman.gd2spring.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import ru.scarletredman.gd2spring.model.Level;
import ru.scarletredman.gd2spring.model.Song;
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
    public LevelListPage getLevels(LevelListPage.Filters filters, int offset) {
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
        var joinUser = rootLevel.<Level, User>join("users", JoinType.INNER);
        var joinSong = rootLevel.<Level, Song>join("songs", JoinType.LEFT);

        query.select(createLevelDTO(criteria, rootLevel, joinUser));

        var criteriaFilters = new ArrayList<Predicate>();
        if (searchLevelName != null) {
            criteriaFilters.add(criteria.like(rootLevel.get("name"), searchLevelName + "%"));
        }
        // todo: implement filters

        query.where(criteriaFilters.toArray(new Predicate[0]));

        var levels = entityManager.createQuery(query).getResultList();

        var total = 0;

        return new LevelListPage(levels, total, offset);
    }

    private CompoundSelection<GDLevelDTO> createLevelDTO(
            CriteriaBuilder criteria, Root<Level> rootLevel, Join<Level, User> joinUser) {
        return criteria.construct(
                GDLevelDTO.class,
                rootLevel.get("id"),
                rootLevel.get("name"),
                rootLevel.get("description"),
                rootLevel.get("version"),
                criteria.construct(GDLevelDTO.User.class, joinUser.get("id"), joinUser.get("username")),
                criteria.construct(
                        LevelFilters.class,
                        rootLevel.get("two_players"),
                        rootLevel.get("has_ldm"),
                        rootLevel.get("length")),
                criteria.construct(
                        LevelRateInfo.class,
                        rootLevel.get("stars"),
                        rootLevel.get("requested_stars"),
                        rootLevel.get("difficulty"),
                        rootLevel.get("rate_time"),
                        rootLevel.get("is_featured"),
                        rootLevel.get("is_epic")),
                rootLevel.get("downloads"),
                rootLevel.get("sound_track"),
                rootLevel.get("likes"),
                rootLevel.get("original"));
    }
}
