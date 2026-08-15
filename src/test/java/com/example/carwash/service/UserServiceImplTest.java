package com.example.carwash.service;

import com.example.carwash.dto.response.UserResponse;
import com.example.carwash.entity.Role;
import com.example.carwash.entity.User;
import com.example.carwash.exception.UserNotFoundException;
import com.example.carwash.mapper.UserMapper;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.security.JwtService;
import com.example.carwash.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

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
        UserResponse response = new UserResponse("ivan", java.util.List.of("ROLE_USER"));
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
}
