package com.erikssonherlo.user.infrastructure.port.output;

import com.erikssonherlo.user.domain.model.User;
import com.erikssonherlo.user.domain.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface UserJpaRepositoryPort {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndRole(String email, String role);

    Optional<User> findByEmailAndAuthCode(String email, String authCode);

    Page<User> findAll(Pageable pageable);

    Page<User> findAllByRole(Role role, Pageable pageable);

    User save(User user, String passwordEncoded);

    User update(String email, User user);

    User generateResetPasswordCode(String email, String authCode, LocalDateTime authCodeExpiration);

    User resetPassword(String email, String newPasswordEncoded);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndAuthCode(String email, String authCode);
}
