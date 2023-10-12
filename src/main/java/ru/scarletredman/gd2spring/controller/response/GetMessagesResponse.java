package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;
import ru.scarletredman.gd2spring.util.JoinResponseUtil;

@Getter
@Setter
@JsonSerialize(using = ResponseSerializer.class)
public class GetMessagesResponse implements ResponseSerializer.Response {

    private long messagesCount;
    private long offset;
    private final Map<Key, Object> elements = new EnumMap<>(Key.class);

    public GetMessagesResponse(long messagesCount, long offset) {
        this.messagesCount = messagesCount;
        this.offset = offset;
        init();
    }

    private void init() {
        // todo
    }

    @Override
    public String getResponse() {
        return JoinResponseUtil.join(elements, "|") + "#" + messagesCount + ":" + offset + ":10";
    }

    @Getter
    @RequiredArgsConstructor
    public enum Key implements JoinResponseUtil.Key {
        MESSAGE_ID("1"),
        USER_ID1("2"),
        USER_ID2("3"),
        SUBJECT("4"),
        USERNAME("6"),
        UPLOAD_TIME("7"),
        IS_NEW("8"),
        GET_SENT("9"),
        ;

        private final String code;
    }
}
