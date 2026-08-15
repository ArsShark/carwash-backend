package com.example.carwash.service.impl;

import com.example.carwash.dto.request.LoginRequest;
import com.example.carwash.dto.request.RegisterRequest;
import com.example.carwash.dto.response.AuthResponse;
import com.example.carwash.dto.response.UserResponse;
import com.example.carwash.entity.Role;
import com.example.carwash.entity.User;
import com.example.carwash.exception.UserAlreadyExistsException;
import com.example.carwash.exception.UserNotFoundException;
import com.example.carwash.mapper.UserMapper;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.security.JwtService;
import com.example.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("User with username '" + request.getUsername() + "' already exists");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role ROLE_USER not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(userRole));
        user.setDeleted(false);

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getUsername());
        return userMapper.toResponse(token, savedUser.getUsername());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + request.getUsername()));

        String token = jwtService.generateToken(user.getUsername());
        return userMapper.toResponse(token, user.getUsername());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    @Override
    public UserResponse getCurrentUser(String username) {
        User user = findByUsername(username);
        return userMapper.toUserResponse(user);
    }
}
