package com.erikssonherlo.user.application.usecase;

import com.erikssonherlo.common.application.anotation.UseCase;
import com.erikssonherlo.common.application.exception.ResourceAlreadyExistsException;
import com.erikssonherlo.user.application.dto.RegisterUserDTO;
import com.erikssonherlo.user.domain.model.User;
import com.erikssonherlo.user.domain.model.enums.Role;
import com.erikssonherlo.user.infrastructure.port.input.CreateUser;
import com.erikssonherlo.user.infrastructure.port.input.GenerateResetPasswordCode;
import com.erikssonherlo.user.infrastructure.port.output.UserJpaRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUser {

    private final UserJpaRepositoryPort userJpaRepositoryPort;
    private final GenerateResetPasswordCode generateResetPasswordCode;
    @Override
    @Transactional
    public User createUser(RegisterUserDTO registerUserDTO) {
        // Data entry validation
        if (registerUserDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (registerUserDTO.email() == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        if (registerUserDTO.role() == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        // Validation of the role using Enum
        if (registerUserDTO.role() != Role.ADMINISTRATOR
                && registerUserDTO.role() != Role.STORE
                && registerUserDTO.role() != Role.WAREHOUSE
                && registerUserDTO.role() != Role.SUPERVISOR) {
            throw new IllegalArgumentException("Invalid role: Role must be ADMINISTRATOR, STORE, WAREHOUSE, or SUPERVISOR");
        }

        // Check if the user already exists by email
        if (userJpaRepositoryPort.existsByEmail(registerUserDTO.email())) {
            throw new ResourceAlreadyExistsException("User", "email", registerUserDTO.email());
        }
        // Create the new user using the domain model
        User newUser = User.builder()
                .firstName(registerUserDTO.firstName())
                .lastName(registerUserDTO.lastName())
                .email(registerUserDTO.email())
                .role(registerUserDTO.role())
                .build();

        try {
            // Guardamos el nuevo usuario
            User createdUser = userJpaRepositoryPort.save(newUser, null);

            // Generamos el código de restablecimiento de contraseña
            boolean isAuthCodeGenerated = generateResetPasswordCode.generateResetPasswordCode(createdUser.getEmail());

            if (isAuthCodeGenerated) {
                System.out.println("Auth code generated and sent to the user.");
            } else {
                System.err.println("Error on generate reset password code.");
            }

            return createdUser;

        } catch (Exception e) {
            // Capturamos cualquier excepción y la imprimimos
            System.err.println("Error during user creation: " + e.getMessage());
            throw e; // Propagamos la excepción para que se maneje en una capa superior si es necesario
        }
    }
}
