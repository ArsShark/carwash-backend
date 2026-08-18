package com.example.carwash.service;

import com.example.carwash.dto.request.LoginRequest;
import com.example.carwash.dto.request.RegisterRequest;
import com.example.carwash.dto.response.AuthResponse;
import com.example.carwash.dto.response.UserResponse;
import com.example.carwash.entity.User;
import com.example.carwash.exception.UserAlreadyExistsException;
import com.example.carwash.exception.UserNotFoundException;

/**
 * Handles user registration, login, and lookup. New users are always
 * granted {@link com.example.carwash.entity.RoleName#ROLE_USER}.
 */
public interface UserService {

    /**
     * Creates a new user account with an encoded password and the default
     * {@code ROLE_USER} role, then returns a JWT for immediate login.
     *
     * @param request the desired username and plain-text password
     * @return a token and username the caller can use right away
     * @throws UserAlreadyExistsException if the username is already taken
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Authenticates a user against their stored credentials and returns a
     * fresh JWT. Unlike {@link #register}, this does not enforce a minimum
     * password length, since it must accept whatever password an existing
     * account was created with.
     *
     * @param request the username and plain-text password to check
     * @return a token and username for the authenticated user
     * @throws org.springframework.security.core.AuthenticationException if the credentials are invalid
     */
    AuthResponse login(LoginRequest request);

    /**
     * @param username the username to look up
     * @return the matching, non-deleted user
     * @throws UserNotFoundException if no such user exists
     */
    User findByUsername(String username);

    /**
     * @param username the username of the currently authenticated caller
     * @return that user's username and role names
     * @throws UserNotFoundException if no such user exists
     */
    UserResponse getCurrentUser(String username);
}
