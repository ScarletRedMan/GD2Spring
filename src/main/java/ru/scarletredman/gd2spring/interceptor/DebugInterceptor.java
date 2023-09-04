package ru.scarletredman.gd2spring.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Log4j2(topic = "GeometryDash API")
public class DebugInterceptor implements HandlerInterceptor {

    private final static String URI_PREFIX = "/-----------";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!request.getRequestURI().startsWith(URI_PREFIX)) return true;

        log.info("[" + request.getMethod() + "] " + request.getRequestURI().substring(URI_PREFIX.length()));
        var map = request.getParameterMap();

        for (String key: map.keySet()) {
            log.info("   " + key + ": " + Arrays.toString(map.get(key)));
        }
        return true;
    }
}
