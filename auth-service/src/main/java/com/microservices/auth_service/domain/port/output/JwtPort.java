package com.microservices.auth_service.domain.port.output;

import com.microservices.auth_service.domain.model.User;

public interface JwtPort {
    String generateToken(User user);
    String extractEmail(String token);
    boolean isTokenValid(String token, String email);
}
