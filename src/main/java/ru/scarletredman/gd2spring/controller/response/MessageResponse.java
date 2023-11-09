package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
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
public final class MessageResponse implements ResponseSerializer.Response {

    private final Map<Key, Object> elements = new EnumMap<>(Key.class);
    private final boolean error;

    private MessageResponse() {
        error = true;
    }

    public MessageResponse(Message message, boolean isSender) {
        init(message, isSender);
        error = false;
    }

    public static MessageResponse error() {
        return new MessageResponse();
    }

    private void init(Message message, boolean isSender) {
        var target = isSender ? message.getReceiver() : message.getSender();
        var targetId = target.getId();

        elements.put(Key.MESSAGE_ID, message.getId());
        elements.put(Key.TARGET_USER_ID1, targetId);
        elements.put(Key.TARGET_USER_ID2, targetId);
        setSubject(message.getSubject());
        setText(message.getText());
        elements.put(Key.TARGET_USERNAME, target.getUsername());
        elements.put(Key.UPLOAD_TIME, TimeFormatUtil.formatBetween(message.getTime()));
        elements.put(Key.IS_SENDER, isSender ? 1 : 0);
        elements.put(Key.IS_NEW, message.isNew() ? 0 : 1);
    }

    public void setSubject(String subject) {
        elements.put(Key.SUBJECT, Base64.encodeBase64String(subject.getBytes(StandardCharsets.UTF_8)));
    }

    public void setText(String message) {
        elements.put(Key.BODY, Base64.encodeBase64String(message.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String getResponse() {
        if (error) return "-1";
        return JoinResponseUtil.join(elements, ":");
    }

    @Getter
    @RequiredArgsConstructor
    public enum Key implements JoinResponseUtil.Key {
        MESSAGE_ID("1"),
        TARGET_USER_ID1("2"),
        TARGET_USER_ID2("3"),
        SUBJECT("4"),
        BODY("5"),
        TARGET_USERNAME("6"),
        UPLOAD_TIME("7"),
        IS_NEW("8"),
        IS_SENDER("9"),
        ;

        private final String code;
    }
}
