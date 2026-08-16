package com.example.carwash.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void resourceNotFound_returns404() {
        ResponseEntity<ErrorResponse> response =
                handler.handleResourceNotFoundException(new ResourceNotFoundException("Client not found with id: 1"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains("Client not found");
    }

    @Test
    void userNotFound_returns404() {
        ResponseEntity<ErrorResponse> response =
                handler.handleUserNotFoundException(new UserNotFoundException("User not found with username: bob"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void userAlreadyExists_returns409() {
        ResponseEntity<ErrorResponse> response =
                handler.handleUserAlreadyExistsException(new UserAlreadyExistsException("User with username 'bob' already exists"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void badCredentials_returns401NotInternalServerError() {
        ResponseEntity<ErrorResponse> response =
                handler.handleAuthenticationException(new BadCredentialsException("Bad credentials"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Invalid username or password");
    }

    @Test
    void accessDenied_returns403NotInternalServerError() {
        ResponseEntity<ErrorResponse> response =
                handler.handleAccessDeniedException(new AccessDeniedException("Access is denied"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void unexpectedException_returns500() {
        ResponseEntity<ErrorResponse> response =
                handler.handleGeneralException(new RuntimeException("boom"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
