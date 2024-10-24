package com.erikssonherlo.user.application.dto;

import com.erikssonherlo.user.domain.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterUserDTO(
        @NotBlank(message = "First name is mandatory")
        @Size(min = 2, max = 255, message = "First name must be between 2 and 255 characters")
        String firstName,

        @NotBlank(message = "Last name is mandatory")
        @Size(min = 2, max = 255, message = "Last name must be between 2 and 255 characters")
        String lastName,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email must be valid")
        String email,

        @NotNull(message = "Role is mandatory")
        Role role
) {}
