package ru.scarletredman.gd2spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.scarletredman.gd2spring.interceptor.DebugInterceptor;

@Configuration
@DependsOn("debugMode")
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final boolean debugMode;
    private final String gdServerURI;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (debugMode) {
            registry.addInterceptor(new DebugInterceptor(gdServerURI));
        }
    }
}
