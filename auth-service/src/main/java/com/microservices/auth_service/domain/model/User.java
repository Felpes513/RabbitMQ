package com.microservices.auth_service.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {

    private final String id;
    private final String email;
    private final String password;
    private final String role;

    public boolean hasRole(String role) {
        return this.role.equals(role);
    }
}