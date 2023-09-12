package ru.scarletredman.gd2spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.scarletredman.gd2spring.controller.annotation.GeometryDashAPI;
import ru.scarletredman.gd2spring.service.UserBackupService;
import ru.scarletredman.gd2spring.service.exception.UserBackupError;
import ru.scarletredman.gd2spring.service.exception.UserLoginError;
import ru.scarletredman.gd2spring.util.ResponseLogger;

@RestController
@GeometryDashAPI
@RequiredArgsConstructor
public class AccountBackupController {

    private final UserBackupService backupService;
    private final ResponseLogger responseLogger;

    @PostMapping("/database/accounts/backupGJAccountNew.php")
    int backup(
            @RequestParam(name = "userName") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "saveData") String data,
            @RequestParam(name = "secret") String secret) {

        int response;
        try {
            backupService.save(username, password, data);
            response = 1;
        } catch (UserBackupError error) {
            response = -1;
        } catch (UserLoginError error) {
            response = -2;
        }
        return responseLogger.result(response);
    }

    @PostMapping("/database/accounts/syncGJAccountNew.php")
    String sync(
            @RequestParam(name = "userName") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "secret") String secret) {

        String response;
        try {
            response = backupService.load(username, password) + ";21;30;a;a";
        } catch (UserBackupError error) {
            response = "-1";
        } catch (UserLoginError error) {
            response = "-2";
        }
        return responseLogger.result(response);
    }
}
