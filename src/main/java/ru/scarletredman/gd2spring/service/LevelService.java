package ru.scarletredman.gd2spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.scarletredman.gd2spring.model.Level;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.repository.LevelRepository;
import ru.scarletredman.gd2spring.service.exception.LevelError;

@Service
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;

    public Level uploadLevel(Level level) throws LevelError {
        // todo
        return level;
    }

    public void updateLevel(Level level) throws LevelError {}

    public void deleteLevel(Level level) {
        levelRepository.delete(level);
    }

    public void deleteLevel(Level level, User owner) throws LevelError {
        if (!level.getOwner().equals(owner)) {
            throw new LevelError();
        }

        deleteLevel(level);
    }
}
