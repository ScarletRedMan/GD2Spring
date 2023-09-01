package ru.scarletredman.gd2spring.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostgresConfig {

    @Bean
    DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/test")
                .username("test")
                .password("test")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
