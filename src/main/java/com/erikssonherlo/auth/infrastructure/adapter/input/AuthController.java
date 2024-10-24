package com.erikssonherlo.auth.infrastructure.adapter.input;

import com.erikssonherlo.auth.application.dto.LoginDTO;
import com.erikssonherlo.auth.infrastructure.port.input.Login;
import com.erikssonherlo.common.application.response.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@EnableMethodSecurity
@Validated
public class AuthController {
    private final Login login;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginDTO request)
    {
        return ResponseEntity.ok(login.login(request));
    }

}
