package com.erikssonherlo.auth.application.usecase;

import com.erikssonherlo.auth.application.dto.LoginDTO;
import com.erikssonherlo.auth.infrastructure.port.input.Login;
import com.erikssonherlo.common.application.response.AuthResponse;
import com.erikssonherlo.common.application.security.JWTService;
import com.erikssonherlo.user.infrastructure.adapter.output.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase implements Login {
    private final UserJpaRepository userJpaRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        UserDetails user = userJpaRepository.findByEmail(request.email())
                .orElseThrow(()-> new RuntimeException("User not found"));
        System.out.println(user);
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
