package com.erikssonherlo.user.application.dto;

import com.erikssonherlo.user.domain.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ResetPasswordDTO(
        @NotBlank(message = "Password is mandatory")
        @Size(min = 6, max = 255, message = "Password must be at least 6 characters")
        String password,

        @NotBlank(message = "Confirm Password is mandatory")
        @Size(min = 6, max = 255, message = "Password must be at least 6 characters")
        String confirmPassword,

        @NotBlank(message = "Auth Code is mandatory")
        @Size(min = 6, max = 6, message = "Auth Code must be 6 characters")
        String authCode
) {

}
