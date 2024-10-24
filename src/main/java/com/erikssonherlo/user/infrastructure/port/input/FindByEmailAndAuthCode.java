package com.erikssonherlo.user.infrastructure.port.input;

public interface FindByEmailAndAuthCode {
    boolean existsByEmailAndAuthCode(String email, String authCode);
}
