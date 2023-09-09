package ru.scarletredman.gd2spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.scarletredman.gd2spring.controller.annotation.GeometryDashAPI;
import ru.scarletredman.gd2spring.controller.response.LoginResponse;
import ru.scarletredman.gd2spring.controller.response.RegisterResponse;
import ru.scarletredman.gd2spring.security.HashPassword;
import ru.scarletredman.gd2spring.service.UserBackupService;
import ru.scarletredman.gd2spring.service.UserService;
import ru.scarletredman.gd2spring.service.exception.UserBackupError;
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
    private final UserBackupService backupService;
    private final ResponseLogger responseLogger;

    @PostMapping("/accounts/registerGJAccount.php")
    RegisterResponse register(@RequestParam(name = "userName") String username,
                              @RequestParam(name = "password") String password,
                              @RequestParam(name = "email") String email,
                              @RequestParam(name = "secret") String secret) {

        try {
            userService.registerUser(username, password, email);
            return RegisterResponse.SUCCESS;
        } catch (UserRegisterError error) {
            return error.getReason();
        }
    }

    @PostMapping("/accounts/loginGJAccount.php")
    LoginResponse login(@RequestParam(name = "userName") String username,
          @RequestParam(name = "password") String password,
          @RequestParam(name = "udid") String udId,
          @RequestParam(name = "sID") String sid,
          @RequestParam(name = "secret") String secret) {

        try {
            var user = userService.loginUser(username, hashPassword.hash(password, username.toLowerCase()));
            return new LoginResponse(user.getId(), user.getId());
        } catch (UserLoginError error) {
            return new LoginResponse(error.getReason());
        }
    }

    @PostMapping("/accounts/backupGJAccountNew.php")
    int backup(@RequestParam(name = "userName") String username,
                  @RequestParam(name = "password") String password,
                  @RequestParam(name = "saveData") String data,
                  @RequestParam(name = "secret") String secret) {

        try {
            backupService.save(username, password, data);
            return 1;
        } catch (UserBackupError error) {
            return -1;
        } catch (UserLoginError error) {
            return -2;
        }
    }

    @PostMapping("/accounts/syncGJAccountNew.php")
    String sync(@RequestParam(name = "userName") String username,
                @RequestParam(name = "password") String password,
                @RequestParam(name = "secret") String secret) {

        try {
            return backupService.load(username, password);
        } catch (UserBackupError error) {
            return "-1";
        } catch (UserLoginError error) {
            return "-2";
        }
    }

    @PostMapping("/getAccountURL.php")
    String accountDataStorage(@RequestParam(name = "accountID") int userId,
                              @RequestParam(name = "type") int requestType,
                              @RequestParam(name = "secret") String secret) {

        return backupDataServerURL;
    }
}
