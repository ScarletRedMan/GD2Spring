package ru.scarletredman.gd2spring.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.scarletredman.gd2spring.security.authentication.GDAuthentication;
import ru.scarletredman.gd2spring.util.GdPasswordUtil;

@RequiredArgsConstructor
public class GeometryDashAuthenticationFilter extends OncePerRequestFilter {

    private static final String USER_ID_PARAM = "accountID";
    private static final String PASSWORD_PARAM = "gjp";

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        var params = request.getParameterMap();

        if (params.containsKey(USER_ID_PARAM) && params.containsKey(PASSWORD_PARAM)) {
            long userId;
            String password;
            try {
                userId = Long.parseLong(params.get(USER_ID_PARAM)[0]);
                password = GdPasswordUtil.gjpDecode(params.get(PASSWORD_PARAM)[0]);
            } catch (Exception ex) {
                userId = -1;
                password = "";
            }

            if (userId != -1) {
                processAuth(userId, password);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void processAuth(long userId, String password) {
        var auth = authenticationManager.authenticate(new GDAuthentication(userId, password));
        if (auth.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }
}
