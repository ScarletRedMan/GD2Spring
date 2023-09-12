package ru.scarletredman.gd2spring.security.role;

public interface DefaultRoles {

    String AUTHORIZED_DEFAULT_ROLE = "ROLE_USER";

    Role USER = new Role(AUTHORIZED_DEFAULT_ROLE);
}
