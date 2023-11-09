package ru.scarletredman.gd2spring.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.scarletredman.gd2spring.model.Message;
import ru.scarletredman.gd2spring.model.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, CustomMessageRepository {

    List<Message> findBySenderOrderByIdDesc(User sender, Pageable pageable);

    List<Message> findByReceiverOrderByIdDesc(User receiver, Pageable pageable);

    long countBySender(User sender);

    long countByReceiver(User receiver);
}
