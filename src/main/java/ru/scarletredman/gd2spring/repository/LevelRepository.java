package ru.scarletredman.gd2spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.scarletredman.gd2spring.model.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {}
