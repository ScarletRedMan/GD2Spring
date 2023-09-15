package ru.scarletredman.gd2spring.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import ru.scarletredman.gd2spring.controller.response.json.ResponseSerializer;
import ru.scarletredman.gd2spring.model.UserComment;
import ru.scarletredman.gd2spring.service.type.UserCommentPage;
import ru.scarletredman.gd2spring.util.JoinResponseUtil;
import ru.scarletredman.gd2spring.util.TimeFormatUtil;

@Getter
@Setter
@JsonSerialize(using = ResponseSerializer.class)
public class UserCommentsResponse implements ResponseSerializer.Response {

    private static final String COMMENT_SEPARATOR = "|";

    private final boolean error;
    private final UserCommentPage page;

    private UserCommentsResponse() {
        error = true;
        page = new UserCommentPage(new ArrayList<>(), 0, 0);
    }

    public UserCommentsResponse(UserCommentPage page) {
        error = false;
        this.page = page;
    }

    public static UserCommentsResponse errorResponse() {
        return new UserCommentsResponse();
    }

    @Override
    public String getResponse() {
        if (error) return "-1";

        var comments = page.comments();
        if (comments.isEmpty()) {
            return "#0:0:10";
        }

        String[] buffer = new String[comments.size()];
        int i = 0;
        for (UserComment comment : comments) {
            buffer[i++] = encodeComment(comment);
        }
        long remainingComments = page.total() - page.page() * 10L;

        return String.join(COMMENT_SEPARATOR, buffer) + "#" + remainingComments + ":" + page.page() + ":10";
    }

    public static String encodeText(String input) {
        return Base64.encodeBase64String(input.getBytes(StandardCharsets.UTF_8));
    }

    private String encodeComment(UserComment comment) {
        return new CommentEncoder(comment).toString();
    }

    @Getter
    public static final class CommentEncoder {

        private final Map<Element, Object> elements = new EnumMap<>(Element.class);

        public CommentEncoder(UserComment comment) {
            init(comment);
        }

        private void init(UserComment comment) {
            elements.put(Element.PLAYER_ID, comment.getOwner().getId());
            elements.put(Element.TEXT, encodeText(comment.getText()));
            elements.put(Element.LIKES, comment.getLikes());
            elements.put(Element.UNKNOWN_ELEMENT, 0);
            elements.put(Element.DATE, TimeFormatUtil.formatBetween(comment.getTimestamp()));
            elements.put(Element.IS_SPAM, comment.isSpam() ? 1 : 0);
            elements.put(Element.COMMENT_ID, comment.getId());
        }

        @Override
        public String toString() {
            return JoinResponseUtil.join(elements, "~");
        }

        @Getter
        public enum Element implements JoinResponseUtil.Key {
            TEXT("2"),
            PLAYER_ID("3"),
            LIKES("4"),
            UNKNOWN_ELEMENT("5"), // 0
            COMMENT_ID("6"),
            IS_SPAM("7"),
            DATE("9");

            private final String code;

            Element(String code) {
                this.code = code;
            }
        }
    }
}
