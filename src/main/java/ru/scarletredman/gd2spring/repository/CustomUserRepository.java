package ru.scarletredman.gd2spring.repository;

import org.springframework.stereotype.Repository;
import ru.scarletredman.gd2spring.model.User;

@Repository
public interface CustomUserRepository {

    void updateSettingsForUser(User user);
}
