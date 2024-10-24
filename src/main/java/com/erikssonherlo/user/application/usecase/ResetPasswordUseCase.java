package com.erikssonherlo.user.application.usecase;

import com.erikssonherlo.common.application.anotation.UseCase;
import com.erikssonherlo.user.application.dto.ResetPasswordDTO;
import com.erikssonherlo.user.domain.model.User;
import com.erikssonherlo.user.infrastructure.port.input.ResetPassword;
import com.erikssonherlo.user.infrastructure.port.output.UserJpaRepositoryPort;
import com.erikssonherlo.common.application.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class ResetPasswordUseCase implements ResetPassword {
    
    private final UserJpaRepositoryPort userJpaRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public boolean resetPassword(String email, ResetPasswordDTO resetPasswordDTO) {
        // TODO Auto-generated method stub
        try {
            // Verificar si el usuario existe
            Optional<User> user = userJpaRepositoryPort.findByEmail(email);
            
            if (user.isEmpty()) {
                throw new ResourceNotFoundException("User", "email", email);
            }
            
            // Validate DTO
            if (resetPasswordDTO.password() == null || resetPasswordDTO.password().trim().equals("")) {
                throw new IllegalArgumentException("Password cannot be null");
            }

            if (resetPasswordDTO.authCode() == null || resetPasswordDTO.authCode().trim().equals("")) {
                throw new IllegalArgumentException("Authentication code cannot be null");
            }

            if(resetPasswordDTO.confirmPassword() == null || resetPasswordDTO.confirmPassword().trim().equals("")) {
                throw new IllegalArgumentException("Confirm password cannot be null");
            }

            // Verificar si las contraseñas coinciden
            if(!resetPasswordDTO.password().equals(resetPasswordDTO.confirmPassword())) {
                throw new IllegalArgumentException("Passwords do not match");
            }

            // Verificar si el código de autenticación es correcto
            if (!user.get().getAuthCode().equals(resetPasswordDTO.authCode())) {
                throw new IllegalArgumentException("Invalid authentication code");
            }

            // Validate Auth Code Expiration
            if (user.get().getAuthCodeExpiration().isBefore(java.time.LocalDateTime.now())) {
                throw new IllegalArgumentException("Authentication code has expired");
            }

            // Encriptar la contraseña
            String encodedPassword = passwordEncoder.encode(resetPasswordDTO.password());

            // Update password
            userJpaRepositoryPort.resetPassword(email, encodedPassword);

            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error resetting password", e);
        }
    }
}
