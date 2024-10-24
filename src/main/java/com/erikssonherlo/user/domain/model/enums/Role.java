package com.erikssonherlo.user.domain.model.enums;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public enum Role {
    STORE,
    SUPERVISOR,
    WAREHOUSE,
    ADMINISTRATOR;

    public static Role fromString(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MethodArgumentTypeMismatchException(null, Role.class, role, null, e);
        }
    }

}
