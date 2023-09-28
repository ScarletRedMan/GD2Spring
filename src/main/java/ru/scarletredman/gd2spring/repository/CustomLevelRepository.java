package ru.scarletredman.gd2spring.repository;

import ru.scarletredman.gd2spring.service.type.LevelListPage;

public interface CustomLevelRepository {

    LevelListPage getLevels(LevelListPage.Filters filters);
}
