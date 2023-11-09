package ru.scarletredman.gd2spring.repository;

import ru.scarletredman.gd2spring.model.User;

public interface CustomMessageRepository {

    long countNewMessages(User user);
}
