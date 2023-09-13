package ru.scarletredman.gd2spring.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.UserComment;

@Repository
public interface UserCommentRepository extends JpaRepository<UserComment, Long> {

    List<UserComment> findAllByOwnerOrderByTimestampDesc(User user, Pageable pageable);

    long countUserCommentsByOwner(User owner);
}
