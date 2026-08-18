package com.example.carwash.mapper;

import com.example.carwash.dto.response.AuthResponse;
import com.example.carwash.dto.response.UserResponse;
import com.example.carwash.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Converts between {@link User} entities and the DTOs returned by the
 * authentication endpoints.
 */
@Component
public class UserMapper {

    /**
     * @return an {@link AuthResponse} carrying a freshly issued JWT
     */
    public AuthResponse toResponse(String token, String username) {
        return new AuthResponse(token, "Bearer", username);
    }

    public UserResponse toUserResponse(User user) {
        List<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName().name())
                .toList();
        return new UserResponse(user.getUsername(), roleNames);
    }
}
