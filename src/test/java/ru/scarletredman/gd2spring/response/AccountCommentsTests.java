package ru.scarletredman.gd2spring.response;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.scarletredman.gd2spring.controller.response.UserCommentsResponse;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.UserComment;
import ru.scarletredman.gd2spring.service.type.UserCommentPage;

public class AccountCommentsTests {

    private static final String COMMENTS_EMPTY =
            Serializer.serialize(new UserCommentsResponse(new UserCommentPage(new ArrayList<>(), 0, 0)));
    private static final String COMMENTS_1PAGE =
            "2~MjkpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~30~7~0~9~null|2~MjgpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~29~7~0~9~null|2~MjcpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~28~7~0~9~null|2~MjYpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~27~7~0~9~null|2~MjUpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~26~7~0~9~null|2~MjQpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~25~7~0~9~null|2~MjMpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~24~7~0~9~null|2~MjIpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~23~7~0~9~null|2~MjEpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~22~7~0~9~null|2~MjApIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~21~7~0~9~null#30:0:10";
    private static final String COMMENTS_2PAGE =
            "2~MTkpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~20~7~0~9~null|2~MTgpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~19~7~0~9~null|2~MTcpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~18~7~0~9~null|2~MTYpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~17~7~0~9~null|2~MTUpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~16~7~0~9~null|2~MTQpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~15~7~0~9~null|2~MTMpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~14~7~0~9~null|2~MTIpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~13~7~0~9~null|2~MTEpIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~12~7~0~9~null|2~MTApIEhlbGxvIHdvcmxkIQ==~3~1~4~0~5~0~6~11~7~0~9~null#20:1:10";
    private static final String COMMENTS_3PAGE =
            "2~OSkgSGVsbG8gd29ybGQh~3~1~4~0~5~0~6~10~7~0~9~null|2~OCkgSGVsbG8gd29ybGQh~3~1~4~0~5~0~6~9~7~0~9~null|2~NykgSGVsbG8gd29ybGQh~3~1~4~0~5~0~6~8~7~0~9~null|2~NikgSGVsbG8gd29ybGQh~3~1~4~0~5~0~6~7~7~0~9~null|2~NSkgSGVsbG8gd29ybGQh~3~1~4~0~5~0~6~6~7~0~9~null|2~NCkgSGVsbG8gd29ybGQh~3~1~4~0~5~0~6~5~7~0~9~null|2~MykgSGVsbG8gd29ybGQh~3~1~4~0~5~0~6~4~7~0~9~null|2~MikgSGVsbG8gd29ybGQh~3~1~4~0~5~0~6~3~7~0~9~null|2~MSkgSGVsbG8gd29ybGQh~3~1~4~0~5~0~6~2~7~0~9~null|2~MCkgSGVsbG8gd29ybGQh~3~1~4~0~5~0~6~1~7~0~9~null#10:2:10";

    private User createTestUser() {
        var user = new User("test", "qwerty", "m@m.m");
        user.setId(1L);
        return user;
    }

    @Test
    void testUserCommentsResponse() {
        var user = createTestUser();
        var comments = new ArrayList<UserComment>();
        for (int i = 0; i < 30; i++) {
            var comment = new UserComment(user, i + ") Hello world!");
            comment.setId(i + 1L);
            comment.setTimestamp(null);

            comments.add(comment);
        }
        Collections.reverse(comments);

        var empty = new UserCommentsResponse(new UserCommentPage(new ArrayList<>(), 0, 0));
        Assertions.assertEquals(COMMENTS_EMPTY, Serializer.serialize(empty));

        var comments1 = comments.stream().limit(10).toList();
        var page1 = new UserCommentsResponse(new UserCommentPage(comments1, 0, 30));
        Assertions.assertEquals(COMMENTS_1PAGE, Serializer.serialize(page1));

        var comments2 = comments.stream().skip(10).limit(10).toList();
        var page2 = new UserCommentsResponse(new UserCommentPage(comments2, 1, 30));
        Assertions.assertEquals(COMMENTS_2PAGE, Serializer.serialize(page2));

        var comments3 = comments.stream().skip(20).limit(10).toList();
        var page3 = new UserCommentsResponse(new UserCommentPage(comments3, 2, 30));
        Assertions.assertEquals(COMMENTS_3PAGE, Serializer.serialize(page3));
    }
}
