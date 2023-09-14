package ru.scarletredman.gd2spring.controller;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.scarletredman.gd2spring.controller.annotation.GeometryDashAPI;
import ru.scarletredman.gd2spring.security.annotation.GDAuthorizedOnly;
import ru.scarletredman.gd2spring.service.UserService;
import ru.scarletredman.gd2spring.util.GdPasswordUtil;
import ru.scarletredman.gd2spring.util.ResponseLogger;

@GeometryDashAPI
@RestController
@RequiredArgsConstructor
public class RewardController {

    private final ResponseLogger responseLogger;

    @GDAuthorizedOnly
    @PostMapping("/getGJRewards.php")
    String getReward(
            @RequestParam(name = "udid") String uniqueId,
            @RequestParam(name = "rewardType") int rewardType,
            @RequestParam(name = "chk") String userBackupSession,
            @RequestParam(name = "r1") int r1,
            @RequestParam(name = "r2") int r2) {

        var user = UserService.getCurrentUserFromSecurityContextHolder();
        var decodedMagicChk = GdPasswordUtil.xor(
                new String(Base64.decodeBase64(userBackupSession.substring(5)), StandardCharsets.UTF_8), "59182");

        // todo
        return responseLogger.result("-1");
    }

    @GDAuthorizedOnly
    @PostMapping("/getGJChallenges.php")
    String getChallenges(
            @RequestParam(name = "udid") String uniqueId,
            @RequestParam(name = "world") int world,
            @RequestParam(name = "chk") String userBackupSession) {

        var user = UserService.getCurrentUserFromSecurityContextHolder();
        var decodedMagicChk = GdPasswordUtil.xor(
                new String(Base64.decodeBase64(userBackupSession.substring(5)), StandardCharsets.UTF_8), "19847");

        // todo
        return responseLogger.result("-1");
    }
}
