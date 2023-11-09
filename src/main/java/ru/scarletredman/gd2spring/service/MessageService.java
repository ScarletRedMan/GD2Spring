package ru.scarletredman.gd2spring.service;

import java.util.Collection;
import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;
import ru.scarletredman.gd2spring.model.Message;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.service.type.MessageListPage;

public interface MessageService {

    Optional<Message> getMessageById(long id);

    Optional<Message> readMessage(long id);

    MessageListPage getMessages(User user, boolean sent, int page, int limit);

    Message sendMessage(User sender, User receiver, String subject, String text);

    void deleteMessage(User user, Message message) throws AccessDeniedException;

    void deleteMessagesById(User user, Collection<Long> messageIds) throws AccessDeniedException;

    void deleteMessages(User user, Collection<Message> messages) throws AccessDeniedException;

    long countNewMessages(User user);
}
