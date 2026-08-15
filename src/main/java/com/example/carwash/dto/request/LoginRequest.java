package com.example.carwash.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    // No @Size here on purpose: login must accept whatever password an
    // account was actually created with (including pre-existing/seeded
    // accounts that predate the registration password policy). Rejecting
    // a login attempt for being "too short" would incorrectly lock out
    // real accounts and also leaks the password policy to unauthenticated
    // callers.
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
