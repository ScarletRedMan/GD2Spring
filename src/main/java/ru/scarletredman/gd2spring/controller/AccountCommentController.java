package ru.scarletredman.gd2spring.controller;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.scarletredman.gd2spring.controller.annotation.GeometryDashAPI;
import ru.scarletredman.gd2spring.controller.response.DeleteAccountCommentResponse;
import ru.scarletredman.gd2spring.controller.response.PublishAccountCommentResponse;
import ru.scarletredman.gd2spring.controller.response.UserCommentsResponse;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.UserComment;
import ru.scarletredman.gd2spring.security.annotation.GDAuthorizedOnly;
import ru.scarletredman.gd2spring.service.UserCommentService;
import ru.scarletredman.gd2spring.service.UserService;
import ru.scarletredman.gd2spring.service.exception.UserCommentError;
import ru.scarletredman.gd2spring.util.ResponseLogger;

@RestController
@GeometryDashAPI
@RequiredArgsConstructor
public class AccountCommentController {

    private final UserService userService;
    private final UserCommentService userCommentService;
    private final ResponseLogger responseLogger;

    @PostMapping("/getGJAccountComments20.php")
    UserCommentsResponse accountComments(
            @RequestParam(name = "accountID") int accountId,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "total") int total,
            @RequestParam(name = "secret") String secret) {

        User target;
        {
            var temp = userService.findUserById(accountId);
            if (temp.isEmpty()) return responseLogger.result(UserCommentsResponse.errorResponse());
            target = temp.get();
        }

        var comments = userCommentService.getComments(target, page, 10);
        return responseLogger.result(new UserCommentsResponse(comments));
    }

    @GDAuthorizedOnly
    @PostMapping("/uploadGJAccComment20.php")
    PublishAccountCommentResponse publishAccountComment(@RequestParam(name = "comment") String encodedText) {
        var user = UserService.getCurrentUserFromSecurityContextHolder();
        var originalText = new String(Base64.decodeBase64(encodedText), StandardCharsets.UTF_8);
        var comment = new UserComment(user, originalText);

        if (originalText.trim().isEmpty()) {
            return responseLogger.result(PublishAccountCommentResponse.FAIL);
        }

        userCommentService.writeComment(comment);
        return responseLogger.result(PublishAccountCommentResponse.SUCCESS);
    }

    @GDAuthorizedOnly
    @PostMapping("/deleteGJAccComment20.php")
    DeleteAccountCommentResponse deleteAccountComment(
            @RequestParam(name = "commentID") long commentId, @RequestParam(name = "cType") int wtfParam) {

        var user = UserService.getCurrentUserFromSecurityContextHolder();
        try {
            userCommentService.deleteComment(user, commentId);
            return responseLogger.result(DeleteAccountCommentResponse.SUCCESS);
        } catch (UserCommentError error) {
            return responseLogger.result(DeleteAccountCommentResponse.FAILED);
        }
    }
}
