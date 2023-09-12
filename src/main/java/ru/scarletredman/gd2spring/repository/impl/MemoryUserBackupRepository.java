package ru.scarletredman.gd2spring.repository.impl;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.repository.UserBackupRepository;

public class MemoryUserBackupRepository implements UserBackupRepository {

    private final Map<Long, String> storage = new ConcurrentHashMap<>();

    @Override
    public void saveBackup(User user, String data) {
        storage.put(user.getId(), data);
    }

    @Override
    public Optional<String> loadBackup(User user) {
        return Optional.ofNullable(storage.getOrDefault(user.getId(), null));
    }

    @Override
    public boolean hasBackup(User user) {
        return storage.containsKey(user.getId());
    }

    @Override
    public void removeBackup(User user) {
        storage.remove(user.getId());
    }
}
