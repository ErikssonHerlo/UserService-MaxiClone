package com.erikssonherlo.auth.infrastructure.port.input;

import com.erikssonherlo.auth.application.dto.LoginDTO;
import com.erikssonherlo.common.application.anotation.InputPort;
import com.erikssonherlo.common.application.response.AuthResponse;

@InputPort
public interface Login {
    AuthResponse login(LoginDTO request);
}
