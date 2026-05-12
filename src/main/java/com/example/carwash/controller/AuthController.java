package com.example.carwash.controller;

import com.example.carwash.dto.response.AuthResponse;
import com.example.carwash.entity.User;
import com.example.carwash.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Эндпоинты для регистрации и входа")
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Регистрация нового пользователя")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @Operation(summary = "Вход в систему и получение JWT токена")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) {
        return ResponseEntity.ok(userService.login(user.getUsername(), user.getPassword()));
    }
}