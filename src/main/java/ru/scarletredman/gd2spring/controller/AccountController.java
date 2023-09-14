package ru.scarletredman.gd2spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.scarletredman.gd2spring.controller.annotation.GeometryDashAPI;
import ru.scarletredman.gd2spring.controller.response.LoginResponse;
import ru.scarletredman.gd2spring.controller.response.RegisterResponse;
import ru.scarletredman.gd2spring.controller.response.UpdateUserSettingsResponse;
import ru.scarletredman.gd2spring.model.embedable.UserSettings;
import ru.scarletredman.gd2spring.security.HashPassword;
import ru.scarletredman.gd2spring.security.annotation.GDAuthorizedOnly;
import ru.scarletredman.gd2spring.service.UserService;
import ru.scarletredman.gd2spring.service.exception.UserLoginError;
import ru.scarletredman.gd2spring.service.exception.UserRegisterError;
import ru.scarletredman.gd2spring.util.ResponseLogger;

@RestController
@GeometryDashAPI
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    private final HashPassword hashPassword;
    private final String backupDataServerURL;
    private final ResponseLogger responseLogger;

    @PostMapping("/accounts/registerGJAccount.php")
    RegisterResponse register(
            @RequestParam(name = "userName") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "secret") String secret) {

        RegisterResponse response;
        try {
            userService.registerUser(username, password, email);
            response = RegisterResponse.SUCCESS;
        } catch (UserRegisterError error) {
            response = error.getReason();
        }
        return responseLogger.result(response);
    }

    @PostMapping("/accounts/loginGJAccount.php")
    LoginResponse login(
            @RequestParam(name = "userName") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "udid") String udId,
            @RequestParam(name = "sID") String sid,
            @RequestParam(name = "secret") String secret) {

        LoginResponse response;
        try {
            var user = userService.loginUser(username, hashPassword.hash(password, username.toLowerCase()));
            response = new LoginResponse(user.getId(), user.getId());
        } catch (UserLoginError error) {
            response = new LoginResponse(error.getReason());
            responseLogger.result("HASHED PASSWORD: " + hashPassword.hash(password, username.toLowerCase()));
        }
        return responseLogger.result(response);
    }

    @PostMapping("/getAccountURL.php")
    String accountDataStorage(
            @RequestParam(name = "accountID") int userId,
            @RequestParam(name = "type") int requestType,
            @RequestParam(name = "secret") String secret) {

        return responseLogger.result(backupDataServerURL);
    }

    @GDAuthorizedOnly
    @PostMapping("/updateGJAccSettings20.php")
    UpdateUserSettingsResponse updateSettings(
            @RequestParam(name = "mS") int messages,
            @RequestParam(name = "frS") int friend,
            @RequestParam(name = "cS") int comments,
            @RequestParam(name = "yt") String youtube,
            @RequestParam(name = "twitter") String twitter,
            @RequestParam(name = "twitch") String twitch,
            @RequestParam(name = "secret") String secret) {

        var user = UserService.getCurrentUserFromSecurityContextHolder();
        user.setYoutubeUrl(youtube);
        user.setTwitchUrl(twitch);
        user.setTwitterUrl(twitter);

        var settings = user.getUserSettings();
        try {
            settings.setAllowFriendRequestsFrom(UserSettings.AllowFriendRequestsFrom.fromValue(friend));
            settings.setAllowMessagesFrom(UserSettings.AllowMessagesFrom.fromValue(messages));
            settings.setShowCommentHistoryTo(UserSettings.ShowCommentHistoryTo.fromValue(comments));
        } catch (IllegalArgumentException ex) {
            return responseLogger.result(UpdateUserSettingsResponse.FAIL);
        }

        userService.updateSettings(user);
        return responseLogger.result(UpdateUserSettingsResponse.SUCCESS);
    }
}
