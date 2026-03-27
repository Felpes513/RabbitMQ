package com.microservices.auth_service.infrastructure.adapter.output.persistence;

import com.microservices.auth_service.domain.model.User;
import com.microservices.auth_service.domain.port.output.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryPort {

    private final SpringUserRepository springUserRepository;

    @Override
    public User save(User user) {
        UserDocument document = toDocument(user);
        UserDocument saved = springUserRepository.save(document);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springUserRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springUserRepository.findByEmail(email).isPresent();
    }

    private UserDocument toDocument(User user) {
        return UserDocument.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    private User toDomain(UserDocument document) {
        return User.builder()
                .id(document.getId())
                .email(document.getEmail())
                .password(document.getPassword())
                .role(document.getRole())
                .build();
    }
}