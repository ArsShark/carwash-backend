package com.example.carwash.controller;

import com.example.carwash.dto.request.LoginRequest;
import com.example.carwash.dto.request.RegisterRequest;
import com.example.carwash.dto.response.AuthResponse;
import com.example.carwash.dto.response.UserResponse;
import com.example.carwash.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoints for registration, login, and the current user's profile.
 * {@code /register} and {@code /login} are public; every other endpoint
 * here requires a valid JWT.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Registration and login endpoints")
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @Operation(summary = "Log in and obtain a JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @Operation(summary = "Get the currently authenticated user")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Authentication authentication) {
        return ResponseEntity.ok(userService.getCurrentUser(authentication.getName()));
    }
}
