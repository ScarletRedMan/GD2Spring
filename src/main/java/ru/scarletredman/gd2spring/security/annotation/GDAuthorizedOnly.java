package ru.scarletredman.gd2spring.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("isAuthenticated()")
public @interface GDAuthorizedOnly {}
