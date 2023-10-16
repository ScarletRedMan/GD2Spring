package ru.scarletredman.gd2spring.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.scarletredman.gd2spring.model.Message;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.rabbit.MQEventPublisher;
import ru.scarletredman.gd2spring.rabbit.response.message.MessageSendMQResponse;
import ru.scarletredman.gd2spring.repository.MessageRepository;
import ru.scarletredman.gd2spring.service.MessageService;
import ru.scarletredman.gd2spring.service.type.MessageListPage;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;
    private final MQEventPublisher eventPublisher;

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<Message> getMessageById(long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    @Override
    public MessageListPage getMessages(User user, boolean sent, int page, int limit) {
        List<Message> messages;
        long total;

        if (sent) {
            messages = repository.findBySenderOrderByIdDesc(user, PageRequest.of(page, limit));
            total = repository.countBySender(user);
        } else {
            messages = repository.findByReceiverOrderByIdDesc(user, PageRequest.of(page, limit));
            total = repository.countByReceiver(user);
        }

        return new MessageListPage(messages, page, total);
    }

    @Transactional
    @Override
    public Message sendMessage(User sender, User receiver, String subject, String text) {
        var message = repository.save(new Message(sender, receiver, subject, text));
        eventPublisher.publish(new MessageSendMQResponse(message));
        return message;
    }

    @Transactional(rollbackFor = AccessDeniedException.class)
    @Override
    public void deleteMessage(User user, Message message) throws AccessDeniedException {
        if (!Objects.equals(message.getReceiver().getId(), user.getId())
                && !Objects.equals(message.getSender().getId(), user.getId())) {
            throw new AccessDeniedException("User is not sender or receiver");
        }

        repository.delete(message);
    }

    @Transactional(rollbackFor = AccessDeniedException.class)
    @Override
    public void deleteMessagesById(User user, Collection<Long> messageIds) throws AccessDeniedException {
        deleteMessages(user, repository.findAllById(messageIds));
    }

    @Transactional(rollbackFor = AccessDeniedException.class)
    @Override
    public void deleteMessages(User user, Collection<Message> messages) throws AccessDeniedException {
        var newList = messages.stream()
                .peek(message -> {
                    if (!Objects.equals(message.getReceiver().getId(), user.getId())
                            && !Objects.equals(message.getSender().getId(), user.getId())) {
                        throw new AccessDeniedException("User is not sender or receiver");
                    }
                })
                .toList();

        repository.deleteAll(newList);
    }
}
