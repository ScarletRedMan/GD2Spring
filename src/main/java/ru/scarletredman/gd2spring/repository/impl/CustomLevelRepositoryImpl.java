package ru.scarletredman.gd2spring.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import ru.scarletredman.gd2spring.repository.CustomLevelRepository;
import ru.scarletredman.gd2spring.service.type.LevelListPage;

public class CustomLevelRepositoryImpl implements CustomLevelRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public LevelListPage getLevels(LevelListPage.Filters filters) {
        // todo
        return new LevelListPage(new ArrayList<>(), 0, 0);
    }
}
