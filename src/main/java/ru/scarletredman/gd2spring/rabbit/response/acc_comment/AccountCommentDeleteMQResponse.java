package ru.scarletredman.gd2spring.rabbit.response.acc_comment;

import lombok.Getter;
import ru.scarletredman.gd2spring.model.UserComment;
import ru.scarletredman.gd2spring.rabbit.response.EventMQResponse;

@Getter
public class AccountCommentDeleteMQResponse extends EventMQResponse {

    private final long userId;
    private final String username;
    private final String commentText;

    public AccountCommentDeleteMQResponse(UserComment comment) {
        super("acc-comment.delete");
        userId = comment.getOwner().getId();
        username = comment.getOwner().getUsername();
        commentText = comment.getText();
    }
}
