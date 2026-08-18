package com.example.carwash.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * The currently authenticated user's identity, as returned by
 * {@code GET /api/auth/me}.
 */
public class UserResponse {
    private String username;
    private List<String> roles;
}
