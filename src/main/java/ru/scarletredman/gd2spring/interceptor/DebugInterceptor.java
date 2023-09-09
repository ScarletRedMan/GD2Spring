package ru.scarletredman.gd2spring.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Arrays;

@Log4j2(topic = "GeometryDash API (Input)")
@RequiredArgsConstructor
public class DebugInterceptor implements HandlerInterceptor {

    private final String gdServerURI;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!request.getRequestURI().startsWith(gdServerURI)) return true;

        log.info("[" + request.getMethod() + "] " + request.getRequestURI().substring(gdServerURI.length()));
        var map = request.getParameterMap();

        for (String key: map.keySet()) {
            log.info("   " + key + ": " + Arrays.toString(map.get(key)));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (!request.getRequestURI().startsWith(gdServerURI)) return;
        log.info(response);
    }
}
