package ru.scarletredman.gd2spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.scarletredman.gd2spring.controller.response.ServerInfo;

@RequiredArgsConstructor
@RestController
public class ServerInfoController {

    private final ServerInfo serverInfo;

    @GetMapping("/api")
    ServerInfo serverInfo() {
        return serverInfo;
    }
}
