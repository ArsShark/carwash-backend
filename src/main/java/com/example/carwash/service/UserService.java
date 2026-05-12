package com.example.carwash.service;

import com.example.carwash.dto.response.AuthResponse;
import com.example.carwash.entity.User;

public interface UserService {
    AuthResponse register(User user);
    AuthResponse login(String username, String password);
    @SuppressWarnings("unused")
    User findByUsername(String username);
}