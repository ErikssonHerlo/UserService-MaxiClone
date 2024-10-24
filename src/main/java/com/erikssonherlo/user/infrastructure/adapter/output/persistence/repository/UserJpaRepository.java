package com.erikssonherlo.user.infrastructure.adapter.output.persistence.repository;

import com.erikssonherlo.common.application.anotation.PersistenceAdapter;
import com.erikssonherlo.user.domain.model.enums.Role;
import com.erikssonherlo.user.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@PersistenceAdapter
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndRole(String email, String role);

    Optional<UserEntity> findByEmailAndAuthCode(String email, String authCode);

    Page<UserEntity> findAllByRole(Role role, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByEmailAndAuthCode(String email, String authCode);

    void deleteByEmail(String email);
}
