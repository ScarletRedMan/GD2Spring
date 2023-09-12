package ru.scarletredman.gd2spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.scarletredman.gd2spring.security.authentication.GDAuthenticationProvider;
import ru.scarletredman.gd2spring.security.filter.GeometryDashAuthenticationEntryPoint;
import ru.scarletredman.gd2spring.security.filter.GeometryDashAuthenticationFilter;
import ru.scarletredman.gd2spring.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(new GDAuthenticationProvider(userService));
    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return new GeometryDashAuthenticationEntryPoint();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        http.sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(b -> b.authenticationEntryPoint(authenticationEntryPoint()));
        http.addFilterAfter(
                new GeometryDashAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return userService;
    }

    @Autowired
    void createTestUser(UserService userService, boolean debugMode) {
        if (!debugMode) return;

        userService.registerUser("test", "qwerty", "m@m.m");
    }
}
