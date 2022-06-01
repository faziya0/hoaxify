package com.hoaxify.constraint;


import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Retention(RUNTIME)
@Target(ElementType.PARAMETER)
@AuthenticationPrincipal
public @interface CurrentUser {

}
