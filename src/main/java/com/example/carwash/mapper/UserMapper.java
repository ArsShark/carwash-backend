package com.example.carwash.mapper;

import com.example.carwash.dto.response.AuthResponse;
import com.example.carwash.dto.response.UserResponse;
import com.example.carwash.entity.Role;
import com.example.carwash.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public AuthResponse toResponse(String token, String username) {
        // Build the response with the token, type and username
        return new AuthResponse(token, "Bearer", username);
    }

    public UserResponse toUserResponse(User user) {
        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .toList();
        return new UserResponse(user.getUsername(), roleNames);
    }
}
