package com.erikssonherlo.user.infrastructure.port.input;

import com.erikssonherlo.common.application.anotation.InputPort;

@InputPort
public interface DeleteUser {
    void deleteUser(String email);
}
