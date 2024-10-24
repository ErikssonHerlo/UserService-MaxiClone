package com.erikssonherlo.user.infrastructure.port.input;

import com.erikssonherlo.common.application.anotation.InputPort;
import com.erikssonherlo.user.domain.model.User;
import com.erikssonherlo.user.domain.model.enums.Role;

import java.util.Optional;

@InputPort
public interface GetUserByEmailAndRole {
    Optional<User> getUserByEmailAndRole(String email, Role role);
}
