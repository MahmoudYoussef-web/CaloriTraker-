package com.caloriestracker.system.mapper;

import com.caloriestracker.system.dto.response.auth.AuthResponse;
import com.caloriestracker.system.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public AuthResponse toResponse(User user, String token) {
        return AuthResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .token(token)
                .build();
    }
}