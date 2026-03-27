package com.microservices.auth_service.domain.port.input;

import com.microservices.auth_service.domain.model.User;

public interface AuthCase {
    String register(String email, String password);
    String login(String email, String password);
}
