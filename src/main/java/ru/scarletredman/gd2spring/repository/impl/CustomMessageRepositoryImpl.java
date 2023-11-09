package ru.scarletredman.gd2spring.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.repository.CustomMessageRepository;

public class CustomMessageRepositoryImpl implements CustomMessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long countNewMessages(User user) {
        return entityManager
                .createQuery("select count(*) from Message where receiver = :receiver and isNew = true", Long.class)
                .setParameter("receiver", user)
                .getSingleResult();
    }
}
