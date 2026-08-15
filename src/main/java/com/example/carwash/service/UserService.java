package com.example.carwash.service;

import com.example.carwash.dto.request.LoginRequest;
import com.example.carwash.dto.request.RegisterRequest;
import com.example.carwash.dto.response.AuthResponse;
import com.example.carwash.dto.response.UserResponse;
import com.example.carwash.entity.User;

public interface UserService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    User findByUsername(String username);
    UserResponse getCurrentUser(String username);
}
