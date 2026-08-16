package com.example.carwash.service;

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
import com.example.carwash.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Role userRole;

    @BeforeEach
    void setUp() {
        userRole = new Role(1L, "ROLE_USER");

        user = new User();
        user.setId(1L);
        user.setUsername("ivan");
        user.setPassword("encoded-password");
        user.setRoles(Set.of(userRole));
        user.setDeleted(false);
    }

    @Test
    void getCurrentUser_whenUserExists_returnsMappedResponse() {
        UserResponse response = new UserResponse("ivan", List.of("ROLE_USER"));
        when(userRepository.findByUsername("ivan")).thenReturn(Optional.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(response);

        UserResponse result = userService.getCurrentUser("ivan");

        assertThat(result.getUsername()).isEqualTo("ivan");
        assertThat(result.getRoles()).containsExactly("ROLE_USER");
    }

    @Test
    void getCurrentUser_whenUserDoesNotExist_throwsUserNotFoundException() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getCurrentUser("ghost"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("ghost");
    }

    @Test
    void findByUsername_whenUserExists_returnsUser() {
        when(userRepository.findByUsername("ivan")).thenReturn(Optional.of(user));

        User result = userService.findByUsername("ivan");

        assertThat(result.getUsername()).isEqualTo("ivan");
    }

    @Test
    void register_whenUsernameFree_createsUserWithDefaultRoleAndReturnsToken() {
        RegisterRequest request = new RegisterRequest("newuser", "plainPassword");
        AuthResponse expectedResponse = new AuthResponse("jwt-token", "Bearer", "newuser");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("plainPassword")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtService.generateToken("newuser")).thenReturn("jwt-token");
        when(userMapper.toResponse("jwt-token", "newuser")).thenReturn(expectedResponse);

        AuthResponse result = userService.register(request);

        assertThat(result.getToken()).isEqualTo("jwt-token");
        assertThat(result.getUsername()).isEqualTo("newuser");

        ArgumentCaptor<User> savedUserCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(savedUserCaptor.capture());
        User savedUser = savedUserCaptor.getValue();
        assertThat(savedUser.getUsername()).isEqualTo("newuser");
        assertThat(savedUser.getPassword()).isEqualTo("encoded-password");
        assertThat(savedUser.getRoles()).containsExactly(userRole);
        assertThat(savedUser.getDeleted()).isFalse();
    }

    @Test
    void register_whenUsernameTaken_throwsUserAlreadyExistsException() {
        RegisterRequest request = new RegisterRequest("ivan", "plainPassword");
        when(userRepository.existsByUsername("ivan")).thenReturn(true);

        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("ivan");

        verify(userRepository, never()).save(any());
    }

    @Test
    void login_whenCredentialsValid_authenticatesAndReturnsToken() {
        LoginRequest request = new LoginRequest("ivan", "plainPassword");
        AuthResponse expectedResponse = new AuthResponse("jwt-token", "Bearer", "ivan");

        when(userRepository.findByUsername("ivan")).thenReturn(Optional.of(user));
        when(jwtService.generateToken("ivan")).thenReturn("jwt-token");
        when(userMapper.toResponse("jwt-token", "ivan")).thenReturn(expectedResponse);

        AuthResponse result = userService.login(request);

        assertThat(result.getToken()).isEqualTo("jwt-token");
        verify(authenticationManager).authenticate(
                eq(new UsernamePasswordAuthenticationToken("ivan", "plainPassword"))
        );
    }

    @Test
    void login_whenAuthenticationFails_propagatesExceptionWithoutLookingUpUser() {
        LoginRequest request = new LoginRequest("ivan", "wrongPassword");
        doThrow(new org.springframework.security.authentication.BadCredentialsException("Bad credentials"))
                .when(authenticationManager).authenticate(any());

        assertThatThrownBy(() -> userService.login(request))
                .isInstanceOf(org.springframework.security.authentication.BadCredentialsException.class);

        verify(userRepository, never()).findByUsername(any());
    }
}
