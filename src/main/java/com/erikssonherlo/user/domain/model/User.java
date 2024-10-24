package com.erikssonherlo.user.domain.model;

import com.erikssonherlo.common.application.anotation.DomainEntity;
import com.erikssonherlo.user.domain.model.enums.Role;
import io.swagger.v3.oas.annotations.info.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@DomainEntity
@Builder
public class User {
    private String email;
    private String firstName;
    private String lastName;
    private String authCode;
    private LocalDateTime authCodeExpiration;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}


