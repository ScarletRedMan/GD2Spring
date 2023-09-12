package ru.scarletredman.gd2spring.controller.annotation;

import java.lang.annotation.*;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@RequestMapping("/-----------") // TODO: put placeholder with URI from config
public @interface GeometryDashAPI {

    @AliasFor(annotation = Component.class)
    String value() default "";
}
