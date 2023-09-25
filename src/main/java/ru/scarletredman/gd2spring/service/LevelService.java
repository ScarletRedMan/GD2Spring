package ru.scarletredman.gd2spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.scarletredman.gd2spring.model.Level;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.repository.LevelRepository;
import ru.scarletredman.gd2spring.service.exception.LevelError;
import ru.scarletredman.gd2spring.service.type.LevelListPage;

@Service
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;

    @Transactional(rollbackFor = LevelError.class)
    public Level uploadLevel(Level level) throws LevelError {
        // todo: validation
        levelRepository.save(level);
        return level;
    }

    @Transactional(rollbackFor = LevelError.class)
    public void updateLevel(Level level) throws LevelError {}

    @Transactional
    public void deleteLevel(Level level) {
        levelRepository.delete(level);
    }

    @Transactional(rollbackFor = LevelError.class)
    public void deleteLevel(Level level, User owner) throws LevelError {
        if (!level.getOwner().equals(owner)) {
            throw new LevelError();
        }

        deleteLevel(level);
    }

    @Transactional(readOnly = true)
    public LevelListPage getLevels(LevelListPage.Filters filters, int offset) {
        return levelRepository.getLevels(filters, offset);
    }
}
