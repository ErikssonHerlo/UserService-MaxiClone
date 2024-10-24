package com.erikssonherlo.user.infrastructure.port.input;

import com.erikssonherlo.common.application.anotation.InputPort;
import com.erikssonherlo.user.domain.model.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@InputPort
public interface GetUserInfo {
    Optional<User> getUserInfo(Authentication authentication);
}
