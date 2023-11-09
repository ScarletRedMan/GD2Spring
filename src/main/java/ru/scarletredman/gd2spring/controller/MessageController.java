package ru.scarletredman.gd2spring.controller;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.scarletredman.gd2spring.controller.annotation.GeometryDashAPI;
import ru.scarletredman.gd2spring.controller.response.GetMessagesResponse;
import ru.scarletredman.gd2spring.controller.response.MessageResponse;
import ru.scarletredman.gd2spring.controller.response.RemoveMessageResponse;
import ru.scarletredman.gd2spring.controller.response.UploadMessageResponse;
import ru.scarletredman.gd2spring.security.annotation.GDAuthorizedOnly;
import ru.scarletredman.gd2spring.service.MessageService;
import ru.scarletredman.gd2spring.service.UserService;
import ru.scarletredman.gd2spring.util.ResponseLogger;

@GeometryDashAPI
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final ResponseLogger responseLogger;

    @GDAuthorizedOnly
    @PostMapping("/getGJMessages20.php")
    GetMessagesResponse messages(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "total") long total,
            @RequestParam(name = "getSent", required = false, defaultValue = "0") int isSent) {

        if (page < 0) return responseLogger.result(GetMessagesResponse.error());

        var user = UserService.getCurrentUserFromSecurityContextHolder();
        var sent = isSent == 1;
        var messages = messageService.getMessages(user, isSent == 1, page, 10);

        return responseLogger.result(
                new GetMessagesResponse(messages.messages(), sent, messages.total(), messages.page()));
    }

    @GDAuthorizedOnly
    @PostMapping("/downloadGJMessage20.php")
    MessageResponse download(
            @RequestParam(name = "messageID") long messageId, @RequestParam(name = "isSender") int isSender) {

        var user = UserService.getCurrentUserFromSecurityContextHolder();
        var message = messageService.readMessage(messageId);

        if (message.isEmpty()) {
            return responseLogger.result(MessageResponse.error());
        }
        var sender = isSender == 1;
        var msg = message.get();

        if ((sender && !Objects.equals(msg.getSender().getId(), user.getId()))
                || (!sender && !Objects.equals(msg.getReceiver().getId(), user.getId()))) {
            return responseLogger.result(MessageResponse.error());
        }

        return responseLogger.result(new MessageResponse(msg, sender));
    }

    @GDAuthorizedOnly
    @PostMapping("/uploadGJMessage20.php")
    UploadMessageResponse upload(
            @RequestParam(name = "toAccountID") long receiverUserId,
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "body") String body) {

        var user = UserService.getCurrentUserFromSecurityContextHolder();
        var receiver = userService.findUserById(receiverUserId);

        if (receiver.isEmpty()) {
            return responseLogger.result(UploadMessageResponse.error());
        }

        var decodedSubject = new String(Base64.decodeBase64(subject), StandardCharsets.UTF_8).replace('\0', ' ');
        var decodedText = new String(Base64.decodeBase64(body), StandardCharsets.UTF_8).replace('\0', ' ');

        messageService.sendMessage(user, receiver.get(), decodedSubject, decodedText);
        return responseLogger.result(UploadMessageResponse.success());
    }

    @GDAuthorizedOnly
    @PostMapping("/deleteGJMessages20.php")
    RemoveMessageResponse delete(
            @RequestParam(name = "messageID", defaultValue = "-1", required = false) long messageId,
            @RequestParam(name = "messages", defaultValue = "", required = false) String messagesList) {

        var user = UserService.getCurrentUserFromSecurityContextHolder();
        if (messageId == -1 && messagesList.isEmpty()) return responseLogger.result(RemoveMessageResponse.success());

        try {
            if (messageId > 0) {
                var message = messageService.getMessageById(messageId);
                message.ifPresent(msg -> messageService.deleteMessage(user, msg));
            }

            if (!messagesList.isEmpty()) {
                var ids = Arrays.stream(messagesList.split(","))
                        .map(num -> {
                            try {
                                return Long.parseLong(num);
                            } catch (NumberFormatException ex) {
                                return -1L;
                            }
                        })
                        .filter(num -> num != -1)
                        .toList();

                messageService.deleteMessagesById(user, ids);
            }
        } catch (AccessDeniedException ex) {
            return responseLogger.result(RemoveMessageResponse.error());
        }

        return responseLogger.result(RemoveMessageResponse.success());
    }
}
