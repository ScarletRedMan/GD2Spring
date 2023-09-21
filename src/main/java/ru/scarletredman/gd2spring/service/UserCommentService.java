package ru.scarletredman.gd2spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.UserComment;
import ru.scarletredman.gd2spring.rabbit.MQEventPublisher;
import ru.scarletredman.gd2spring.rabbit.response.acc_comment.AccountCommentDeleteMQResponse;
import ru.scarletredman.gd2spring.rabbit.response.acc_comment.AccountCommentPublishMQResponse;
import ru.scarletredman.gd2spring.repository.UserCommentRepository;
import ru.scarletredman.gd2spring.service.exception.UserCommentError;
import ru.scarletredman.gd2spring.service.type.UserCommentPage;

@Service
@RequiredArgsConstructor
public class UserCommentService {

    private final UserCommentRepository userCommentRepository;
    private final MQEventPublisher eventPublisher;

    @Transactional
    public void writeComment(UserComment comment) {
        userCommentRepository.save(comment);
        eventPublisher.publish(new AccountCommentPublishMQResponse(comment));
    }

    @Transactional(rollbackFor = {UserCommentError.class})
    public void deleteComment(User user, long commentId) throws UserCommentError {
        UserComment comment;
        {
            var temp = userCommentRepository.findById(commentId);
            if (temp.isEmpty()) return;
            comment = temp.get();
        }

        if (!comment.getOwner().equals(user)) {
            throw new UserCommentError();
        }

        userCommentRepository.delete(comment);
        eventPublisher.publish(new AccountCommentDeleteMQResponse(comment));
    }

    @Transactional
    public UserCommentPage getComments(User user, int page, int size) {
        var comments = userCommentRepository.findAllByOwnerOrderByTimestampDescIdDesc(user, PageRequest.of(page, size));
        var total = userCommentRepository.countUserCommentsByOwner(user);

        return new UserCommentPage(comments, page, total);
    }

    public void likeComment(User user, UserComment comment) {}
}
