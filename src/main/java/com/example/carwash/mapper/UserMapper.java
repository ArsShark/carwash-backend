package com.example.carwash.mapper;

import com.example.carwash.dto.response.AuthResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public AuthResponse toResponse(String token, String username) {
        // Build the response with the token, type and username
        return new AuthResponse(token, "Bearer", username);
    }
}
