package ru.scarletredman.gd2spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.scarletredman.gd2spring.model.Song;

public interface SongRepository extends JpaRepository<Song, Long> {}
