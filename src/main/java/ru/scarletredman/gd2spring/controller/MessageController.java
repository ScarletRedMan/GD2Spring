package ru.scarletredman.gd2spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.scarletredman.gd2spring.controller.annotation.GeometryDashAPI;
import ru.scarletredman.gd2spring.security.annotation.GDAuthorizedOnly;
import ru.scarletredman.gd2spring.service.UserService;
import ru.scarletredman.gd2spring.util.ResponseLogger;

@GeometryDashAPI
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final ResponseLogger responseLogger;

    @GDAuthorizedOnly
    @PostMapping("/getGJMessages20.php")
    String messages(@RequestParam(name = "page") int page, @RequestParam(name = "total") long total) {
        var user = UserService.getCurrentUserFromSecurityContextHolder();

        return "-1";
    }

    @GDAuthorizedOnly
    @PostMapping("/downloadGJMessage20.php")
    String download(@RequestParam(name = "messageID") long messageId, @RequestParam(name = "isSender") int isSender) {
        var user = UserService.getCurrentUserFromSecurityContextHolder();

        return "-1";
    }

    @GDAuthorizedOnly
    @PostMapping("/uploadGJMessage20.php")
    String upload(
            @RequestParam(name = "toAccountID") long receiverUserId,
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "body") String body) {

        var user = UserService.getCurrentUserFromSecurityContextHolder();

        return "-1";
    }

    @GDAuthorizedOnly
    @PostMapping("/deleteGJMessages.php")
    String delete(
            @RequestParam(name = "messageID", defaultValue = "-1", required = false) long messageId,
            @RequestParam(name = "messages", defaultValue = "", required = false) String messagesList) {

        var user = UserService.getCurrentUserFromSecurityContextHolder();

        return "-1";
    }
}
