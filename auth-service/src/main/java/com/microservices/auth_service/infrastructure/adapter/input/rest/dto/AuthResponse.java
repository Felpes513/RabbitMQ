package com.microservices.auth_service.infrastructure.adapter.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String tokenType;

    public static AuthResponse of(String token) {
        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .build();
    }
}
