package com.erikssonherlo.auth.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDTO(
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "Password is mandatory")
        @NotNull(message = "Password is mandatory")
        String password
) {
}
