package ru.scarletredman.gd2spring.security.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import ru.scarletredman.gd2spring.service.UserService;
import ru.scarletredman.gd2spring.service.exception.UserLoginError;

@RequiredArgsConstructor
public class GDAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var auth = (GDAuthentication) authentication;

        try {
            var user = userService.loginUser(auth.userId(), auth.rawPassword());

            return UsernamePasswordAuthenticationToken.authenticated(user, auth.rawPassword(), user.getAuthorities());
        } catch (UserLoginError error) {
            return UsernamePasswordAuthenticationToken.unauthenticated(null, null);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isNestmateOf(GDAuthentication.class);
    }
}
