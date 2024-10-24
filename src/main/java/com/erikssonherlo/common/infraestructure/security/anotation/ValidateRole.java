package com.erikssonherlo.common.infraestructure.security.anotation;

import com.erikssonherlo.user.domain.model.enums.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateRole {
    String[] value();
}