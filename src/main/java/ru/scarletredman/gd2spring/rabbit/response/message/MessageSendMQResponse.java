package ru.scarletredman.gd2spring.rabbit.response.message;

import java.sql.Timestamp;
import lombok.Getter;
import ru.scarletredman.gd2spring.model.Message;
import ru.scarletredman.gd2spring.rabbit.response.EventMQResponse;

@Getter
public class MessageSendMQResponse extends EventMQResponse {

    private final long id;
    private final long senderUserId;
    private final String senderUsername;
    private final long receiverUserId;
    private final String receiverUsername;
    private final String subject;
    private final String body;
    private final Timestamp timestamp;

    public MessageSendMQResponse(Message message) {
        super("message.send");
        id = message.getId();
        subject = message.getSubject();
        body = message.getText();
        timestamp = message.getTime();

        var sender = message.getSender();
        senderUserId = sender.getId();
        senderUsername = sender.getUsername();

        var receiver = message.getReceiver();
        receiverUserId = receiver.getId();
        receiverUsername = receiver.getUsername();
    }
}
