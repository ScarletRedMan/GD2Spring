package ru.scarletredman.gd2spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.scarletredman.gd2spring.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsernameIgnoreCase(String username);

    Optional<User> findUserByEmailIgnoreCase(String email);
}
