package ru.scarletredman.gd2spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.scarletredman.gd2spring.controller.annotation.GeometryDashAPI;
import ru.scarletredman.gd2spring.controller.response.LoginResponse;
import ru.scarletredman.gd2spring.controller.response.RegisterResponse;

@RestController
@GeometryDashAPI
@RequiredArgsConstructor
public class AccountController {

    @PostMapping("/accounts/registerGJAccount.php")
    RegisterResponse register(@RequestParam(name = "userName") String username,
                              @RequestParam(name = "password") String password,
                              @RequestParam(name = "email") String email,
                              @RequestParam(name = "secret") String secret) {

        return RegisterResponse.UNKNOWN_ERROR;
    }

    @PostMapping("/accounts/loginGJAccount.php")
    LoginResponse login(@RequestParam(name = "userName") String login,
          @RequestParam(name = "password") String password,
          @RequestParam(name = "udid") String udId,
          @RequestParam(name = "sID") String sid,
          @RequestParam(name = "secret") String secret) {

        return new LoginResponse(LoginResponse.ErrorReason.LOGIN_FAILED);
    }
}
