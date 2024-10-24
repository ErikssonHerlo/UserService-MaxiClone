package com.erikssonherlo.user.infrastructure.port.input;

import com.erikssonherlo.common.application.anotation.InputPort;
import com.erikssonherlo.user.domain.model.User;
import com.erikssonherlo.user.domain.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@InputPort
public interface GetAllUsersByRole {
    Page<User> getAllUsersByRole(Role role, Pageable pageable);
}
