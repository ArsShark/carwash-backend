package com.example.carwash.mapper;

import com.example.carwash.dto.request.AuthRequest;
import com.example.carwash.dto.response.AuthResponse;
import com.example.carwash.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(AuthRequest request) {
        if (request == null) return null;
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        return user;
    }

    public AuthResponse toResponse(String token, String username) {
        // Создаем ответ с токеном, типом и именем пользователя
        return new AuthResponse(token, "Bearer", username);
    }
}