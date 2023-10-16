package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;
import ru.scarletredman.gd2spring.model.Message;
import ru.scarletredman.gd2spring.util.JoinResponseUtil;
import ru.scarletredman.gd2spring.util.TimeFormatUtil;

@Getter
@Setter
@JsonSerialize(using = ResponseSerializer.class)
public final class GetMessagesResponse implements ResponseSerializer.Response {

    private long messagesCount;
    private long offset;
    private final List<MessageResponse> messages = new LinkedList<>();
    private final boolean error;

    private GetMessagesResponse() {
        error = true;
    }

    public GetMessagesResponse(List<Message> messages, boolean sent, long messagesCount, long offset) {
        this.messagesCount = messagesCount;
        this.offset = offset;
        init(messages, sent);
        error = false;
    }

    public static GetMessagesResponse error() {
        return new GetMessagesResponse();
    }

    private void init(List<Message> messages, boolean sent) {
        this.messages.addAll(messages.stream()
                .map(message -> new MessageResponse(message, sent))
                .toList());
    }

    @Override
    public String getResponse() {
        if (error) return "-1";
        if (messages.isEmpty()) return "-2";

        var msgList = String.join(
                "|", messages.stream().map(MessageResponse::getResponse).toList());
        return msgList + "#" + messagesCount + ":" + offset + ":10";
    }

    public static class MessageResponse implements ResponseSerializer.Response {

        private final Map<MessageResponse.Key, Object> elements = new EnumMap<>(MessageResponse.Key.class);

        public MessageResponse(Message message, boolean isSender) {
            init(message, isSender);
        }

        private void init(Message message, boolean isSender) {
            var target = isSender ? message.getReceiver() : message.getSender();
            var targetId = target.getId();

            elements.put(MessageResponse.Key.MESSAGE_ID, message.getId());
            elements.put(MessageResponse.Key.TARGET_USER_ID1, targetId);
            elements.put(MessageResponse.Key.TARGET_USER_ID2, targetId);
            setSubject(message.getSubject());
            elements.put(Key.TARGET_USERNAME, target.getUsername());
            elements.put(Key.UPLOAD_TIME, TimeFormatUtil.formatBetween(message.getTime()));
            elements.put(Key.IS_NEW, message.isNew() ? 1 : 0);
            elements.put(Key.IS_SENDER, isSender ? 1 : 0);
        }

        public void setSubject(String subject) {
            elements.put(Key.SUBJECT, Base64.encodeBase64String(subject.getBytes(StandardCharsets.UTF_8)));
        }

        @Override
        public String getResponse() {
            return JoinResponseUtil.join(elements, ":");
        }

        @Getter
        @RequiredArgsConstructor
        public enum Key implements JoinResponseUtil.Key {
            MESSAGE_ID("1"),
            TARGET_USER_ID1("2"),
            TARGET_USER_ID2("3"),
            SUBJECT("4"),
            TARGET_USERNAME("6"),
            UPLOAD_TIME("7"),
            IS_NEW("8"),
            IS_SENDER("9"),
            ;

            private final String code;
        }
    }
}
