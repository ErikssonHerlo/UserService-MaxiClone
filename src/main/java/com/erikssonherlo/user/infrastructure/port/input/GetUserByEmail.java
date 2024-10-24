package com.erikssonherlo.user.infrastructure.port.input;

import com.erikssonherlo.common.application.anotation.InputPort;
import com.erikssonherlo.user.domain.model.User;

import java.util.Optional;

@InputPort
public interface GetUserByEmail {
    Optional<User> getUserByEmail(String email);
}
