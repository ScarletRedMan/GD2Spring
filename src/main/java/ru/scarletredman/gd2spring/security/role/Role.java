package ru.scarletredman.gd2spring.security.role;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

public record Role(@NonNull String authority) implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return authority;
    }
}
