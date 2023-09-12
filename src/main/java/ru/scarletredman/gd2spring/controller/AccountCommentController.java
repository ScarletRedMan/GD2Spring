package ru.scarletredman.gd2spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.scarletredman.gd2spring.controller.annotation.GeometryDashAPI;
import ru.scarletredman.gd2spring.security.annotation.GDAuthorizedOnly;

@RestController
@GeometryDashAPI
@RequiredArgsConstructor
public class AccountCommentController {

    @GDAuthorizedOnly
    @PostMapping("/getGJAccountComments20.php")
    String accountComments(@RequestParam(name = "accountID") int accountId,
                           @RequestParam(name = "page") int page,
                           @RequestParam(name = "total") int total,
                           @RequestParam(name = "secret") String secret) {
        return "-1";
    }

    @GDAuthorizedOnly
    @PostMapping("/uploadGJAccComment20.php")
    String publishAccountComment(@RequestParam(name = "comment") String encodedText) {
        return "-1";
    }

    @GDAuthorizedOnly
    @PostMapping("/deleteGJAccComment20.php")
    String deleteAccountComment(@RequestParam(name = "commentID") int commentId,
                                @RequestParam(name = "cType") int wtfParam) {
        return "-1";
    }
}
