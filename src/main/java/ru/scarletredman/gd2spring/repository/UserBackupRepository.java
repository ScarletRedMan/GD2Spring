package ru.scarletredman.gd2spring.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import ru.scarletredman.gd2spring.model.User;

@Repository
public interface UserBackupRepository {

    void saveBackup(User user, String data);

    Optional<String> loadBackup(User user);

    boolean hasBackup(User user);

    void removeBackup(User user);
}
