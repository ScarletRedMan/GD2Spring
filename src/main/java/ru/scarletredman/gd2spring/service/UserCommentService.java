package ru.scarletredman.gd2spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.UserComment;
import ru.scarletredman.gd2spring.repository.UserCommentRepository;
import ru.scarletredman.gd2spring.service.type.UserCommentPage;

@Service
@RequiredArgsConstructor
public class UserCommentService {

    private final UserCommentRepository userCommentRepository;

    @Transactional
    public void writeComment(UserComment comment) {
        userCommentRepository.save(comment);
    }

    public void deleteComment(UserComment comment) {}

    @Transactional
    public UserCommentPage getComments(User user, int page, int size) {
        var comments = userCommentRepository.findAllByOwnerOrderByTimestampDesc(user, PageRequest.of(page, size));
        var total = userCommentRepository.countUserCommentsByOwner(user);

        return new UserCommentPage(comments, page, total);
    }

    public void likeComment(User user, UserComment comment) {}
}
