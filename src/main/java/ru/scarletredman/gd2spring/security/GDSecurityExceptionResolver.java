package ru.scarletredman.gd2spring.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import ru.scarletredman.gd2spring.service.exception.UserLoginError;

@Component
public class GDSecurityExceptionResolver extends AbstractHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            Object handler,
            @NonNull Exception ex) {

        var error = ex.getCause();
        if (error instanceof UserLoginError) {
            try {
                response.getWriter().write("-1");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new ModelAndView();
        }
        return null;
    }
}
