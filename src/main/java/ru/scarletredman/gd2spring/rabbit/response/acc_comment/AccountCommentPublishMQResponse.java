package ru.scarletredman.gd2spring.rabbit.response.acc_comment;

import lombok.Getter;
import ru.scarletredman.gd2spring.model.UserComment;
import ru.scarletredman.gd2spring.rabbit.response.EventMQResponse;

@Getter
public class AccountCommentPublishMQResponse extends EventMQResponse {

    private final long userId;
    private final String username;
    private final String text;

    public AccountCommentPublishMQResponse(UserComment comment) {
        super("acc-comment.publish");
        userId = comment.getOwner().getId();
        username = comment.getOwner().getUsername();
        text = comment.getText();
    }
}
