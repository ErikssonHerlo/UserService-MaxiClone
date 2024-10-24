package com.erikssonherlo.user.infrastructure.port.input;

import com.erikssonherlo.common.application.anotation.InputPort;
import com.erikssonherlo.user.application.dto.UpdateUserDTO;
import com.erikssonherlo.user.domain.model.User;

@InputPort
public interface UpdateUser {
    User updateUser(String email, UpdateUserDTO request);
}
