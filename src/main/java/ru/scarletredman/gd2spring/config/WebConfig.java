package ru.scarletredman.gd2spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.scarletredman.gd2spring.interceptor.DebugInterceptor;
import ru.scarletredman.gd2spring.util.ResponseLogger;

@Configuration
@DependsOn("debugMode")
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final boolean debugMode;
    private final String gdServerURI;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        if (debugMode) {
            registry.addInterceptor(new DebugInterceptor(gdServerURI));
        }
    }

    @Bean
    @Autowired
    ResponseLogger responseLogger(ObjectMapper objectMapper) {
        if (debugMode) return new ResponseLogger.DebugResponseLogger(objectMapper.writer());
        return new ResponseLogger.NoneResponseLogger();
    }
}
