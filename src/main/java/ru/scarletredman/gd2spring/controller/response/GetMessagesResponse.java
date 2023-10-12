package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;

@Getter
@Setter
@JsonSerialize(using = ResponseSerializer.class)
public class GetMessagesResponse implements ResponseSerializer.Response {

    private long messagesCount;
    private long offset;
    private final List<MessageResponse> messages = new LinkedList<>();

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
        var msgList = String.join(
                "|", messages.stream().map(MessageResponse::getResponse).toList());
        return msgList + "#" + messagesCount + ":" + offset + ":10";
    }
}
