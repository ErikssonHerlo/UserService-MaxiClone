package com.erikssonherlo.user.infrastructure.port.input;

import com.erikssonherlo.user.application.dto.ResetPasswordDTO;
import com.erikssonherlo.user.domain.model.User;

public interface ResetPassword {
    boolean resetPassword(String email, ResetPasswordDTO resetPasswordDTO);
}
