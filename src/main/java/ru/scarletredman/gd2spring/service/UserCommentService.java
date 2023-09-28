package ru.scarletredman.gd2spring.service;

import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.UserComment;
import ru.scarletredman.gd2spring.service.exception.UserCommentError;
import ru.scarletredman.gd2spring.service.type.UserCommentPage;

public interface UserCommentService {

    void writeComment(UserComment comment);

    void deleteComment(User user, long commentId) throws UserCommentError;

    UserCommentPage getComments(User user, int page, int size);

    void likeComment(User user, UserComment comment);
}
