package com.example.carwash.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * A JWT issued after successful registration or login.
 */
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private String username;
}