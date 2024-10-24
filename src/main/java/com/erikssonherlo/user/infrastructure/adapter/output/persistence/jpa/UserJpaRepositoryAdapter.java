package com.erikssonherlo.user.infrastructure.adapter.output.persistence.jpa;

import com.erikssonherlo.user.domain.model.User;
import com.erikssonherlo.user.domain.model.enums.Role;
import com.erikssonherlo.user.infrastructure.adapter.output.persistence.entity.UserEntity;
import com.erikssonherlo.user.infrastructure.adapter.output.persistence.repository.UserJpaRepository;
import com.erikssonherlo.user.infrastructure.port.output.UserJpaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaRepositoryAdapter implements UserJpaRepositoryPort {

    private final UserJpaRepository userJpaRepository;

    // Encontrar por email
    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(this::mapToDomain);
    }

    // Encontrar por email y rol
    @Override
    public Optional<User> findByEmailAndRole(String email, String role) {
        return userJpaRepository.findByEmailAndRole(email, role)
                .map(this::mapToDomain);
    }

    // Encontrar por email y código de autenticación
    @Override
    public Optional<User> findByEmailAndAuthCode(String email, String authCode) {
        return userJpaRepository.findByEmailAndAuthCode(email, authCode)
                .map(this::mapToDomain);
    }

    // Listar todos los usuarios paginados
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userJpaRepository.findAll(pageable)
                .map(this::mapToDomain);
    }

    // Listar todos los usuarios por rol paginados
    @Override
    public Page<User> findAllByRole(Role role, Pageable pageable) {
        return userJpaRepository.findAllByRole(role, pageable)
                .map(this::mapToDomain);
    }

    // Guardar usuario (create)
    @Override
    public User save(User user, String passwordEncoded) {
        UserEntity userEntity = mapToEntity(user, passwordEncoded);
        UserEntity savedEntity = userJpaRepository.save(userEntity);
        return mapToDomain(savedEntity);
    }

    // Actualizar usuario
    @Override
    public User update(String email, User user) {
        UserEntity existingEntity = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        UserEntity updatedEntity = mapToEntity(user, "");
        updatedEntity.setPassword(existingEntity.getPassword());  // Keep the same password

        UserEntity savedEntity = userJpaRepository.save(updatedEntity);
        return mapToDomain(savedEntity);
    }

    // Generar código de reseteo de contraseña
    @Override
    public User generateResetPasswordCode(String email, String authCode, LocalDateTime authCodeExpiration) {
        UserEntity userEntity = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        userEntity.setAuthCode(authCode);
        userEntity.setAuthCodeExpiration(authCodeExpiration);

        UserEntity savedEntity = userJpaRepository.save(userEntity);
        return mapToDomain(savedEntity);
    }

    // Resetear contraseña
    @Override
    public User resetPassword(String email, String newPasswordEncoded) {
        UserEntity userEntity = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        userEntity.setPassword(newPasswordEncoded);
        userEntity.setAuthCode(null);
        userEntity.setAuthCodeExpiration(null);
        UserEntity savedEntity = userJpaRepository.save(userEntity);
        return mapToDomain(savedEntity);
    }

    // Eliminar usuario por email
    @Override
    public void deleteByEmail(String email) {
        userJpaRepository.deleteByEmail(email);
    }

    // Verificar si existe un usuario por email
    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    // Verificar si existe un usuario por email y código de autenticación
    @Override
    public boolean existsByEmailAndAuthCode(String email, String authCode) {
        return userJpaRepository.existsByEmailAndAuthCode(email, authCode);
    }

    // Método de mapeo de UserEntity a User (dominio)
    private User mapToDomain(UserEntity entity) {
        return User.builder()
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .authCode(entity.getAuthCode())
                .authCodeExpiration(entity.getAuthCodeExpiration())
                .role(entity.getRole())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    // Método de mapeo de User (dominio) a UserEntity
    private UserEntity mapToEntity(User user, String passwordEncoded) {
        return UserEntity.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(passwordEncoded)
                .authCode(user.getAuthCode())
                .authCodeExpiration(user.getAuthCodeExpiration())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .build();
    }
}
