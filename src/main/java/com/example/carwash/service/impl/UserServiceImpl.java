package com.example.carwash.service.impl;

import com.example.carwash.dto.response.AuthResponse;
import com.example.carwash.entity.Role;
import com.example.carwash.entity.User;
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
    public AuthResponse register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("User with username '" + user.getUsername() + "' already exists");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role ROLE_USER not found"));

        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDeleted(false);

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getUsername());
        return userMapper.toResponse(token, savedUser.getUsername());
    }

    @Override
    public AuthResponse login(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        String token = jwtService.generateToken(username);
        return userMapper.toResponse(token, username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }
}