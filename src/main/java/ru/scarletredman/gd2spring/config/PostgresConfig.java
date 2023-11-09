package ru.scarletredman.gd2spring.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("ru.scarletredman.gd2spring.repository")
@Configuration
public class PostgresConfig {

    @Bean
    DataSource dataSource(
            @Value("${GD2SPRING_DATABASE_URL}") String url,
            @Value("${GD2SPRING_DATABASE_USER}") String user,
            @Value("${GD2SPRING_DATABASE_PASSWORD}") String password) {

        return DataSourceBuilder.create()
                .url(url)
                .username(user)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
