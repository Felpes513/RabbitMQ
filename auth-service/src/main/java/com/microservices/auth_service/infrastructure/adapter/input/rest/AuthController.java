package com.microservices.auth_service.infrastructure.adapter.input.rest;

import com.microservices.auth_service.domain.port.input.AuthCase;
import com.microservices.auth_service.infrastructure.adapter.input.rest.dto.AuthResponse;
import com.microservices.auth_service.infrastructure.adapter.input.rest.dto.LoginRequest;
import com.microservices.auth_service.infrastructure.adapter.input.rest.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthCase authUseCase;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        String token = authUseCase.register(request.getEmail(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(AuthResponse.of(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authUseCase.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(AuthResponse.of(token));
    }
}