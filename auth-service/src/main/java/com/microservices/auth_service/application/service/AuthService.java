package com.microservices.auth_service.application.service;

import com.microservices.auth_service.domain.model.User;
import com.microservices.auth_service.domain.port.input.AuthCase;
import com.microservices.auth_service.domain.port.output.JwtPort;
import com.microservices.auth_service.domain.port.output.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthCase {

    private final UserRepositoryPort userRepository;
    private final JwtPort jwtPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(String email, String password){
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email já cadastrado!");
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role("ROLE_USER")
                .build();
        userRepository.save(user);

        return jwtPort.generateToken(user);
    }

    @Override
    public String login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Credenciais inválidas!");
        }

        return jwtPort.generateToken(user);
    }
}
