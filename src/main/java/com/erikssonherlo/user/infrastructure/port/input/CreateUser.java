package com.erikssonherlo.user.infrastructure.port.input;

import com.erikssonherlo.common.application.anotation.InputPort;
import com.erikssonherlo.user.application.dto.RegisterUserDTO;
import com.erikssonherlo.user.domain.model.User;

@InputPort
public interface CreateUser {
    User createUser(RegisterUserDTO user);
}
