package ru.scarletredman.gd2spring.service;

import ru.scarletredman.gd2spring.model.Level;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.service.exception.LevelError;
import ru.scarletredman.gd2spring.service.type.LevelListPage;

public interface LevelService {

    Level uploadLevel(Level level) throws LevelError;

    void updateLevel(Level level) throws LevelError;

    void deleteLevel(Level level);

    void deleteLevel(Level level, User owner) throws LevelError;

    LevelListPage getLevels(LevelListPage.Filters filters);
}
