package ru.scarletredman.gd2spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.scarletredman.gd2spring.controller.response.ServerInfo;
import ru.scarletredman.gd2spring.security.HashPassword;
import ru.scarletredman.gd2spring.security.Sha256HashPassword;

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

    @Bean
    boolean debugMode() {
        return true;
    }

    @Bean
    HashPassword hashPassword() {
        return new Sha256HashPassword();
    }
}
