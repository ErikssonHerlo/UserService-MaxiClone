package com.erikssonherlo.user.infrastructure.port.input;

import java.time.LocalDateTime;

public interface GenerateResetPasswordCode {
    boolean generateResetPasswordCode(String email);
}
