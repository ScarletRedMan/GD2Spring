package ru.scarletredman.gd2spring.rabbit.response.acc_comment;

import lombok.Getter;
import ru.scarletredman.gd2spring.model.UserComment;
import ru.scarletredman.gd2spring.rabbit.response.EventResponse;

@Getter
public class AccountCommentPublishResponse extends EventResponse {

    private final long userId;
    private final String username;
    private final String text;

    public AccountCommentPublishResponse(UserComment comment) {
        super("acc-comment.publish");
        userId = comment.getOwner().getId();
        username = comment.getOwner().getUsername();
        text = comment.getText();
    }
}
