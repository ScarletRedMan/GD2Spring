package ru.scarletredman.gd2spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.scarletredman.gd2spring.controller.response.ServerInfo;

@Configuration
public class GeometryDashServerConfig {

    @Bean
    ServerInfo serverInfo() {
        return new ServerInfo("GD2Spring", "http://localhost:8080");
    }

    @Bean
    String gdServerURI() {
        return serverInfo().getGdServerURI();
    }
}
