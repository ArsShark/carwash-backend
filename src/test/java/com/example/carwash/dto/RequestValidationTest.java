package com.example.carwash.dto;

import com.example.carwash.dto.request.AppointmentRequest;
import com.example.carwash.dto.request.RegisterRequest;
import com.example.carwash.dto.request.ClientRequest;
import com.example.carwash.dto.request.ServiceRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that the Bean Validation annotations declared on request DTOs
 * actually reject invalid input, since this is what the assignment report claims.
 */
class RequestValidationTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    void clientRequest_blankFullName_isRejected() {
        ClientRequest request = new ClientRequest("", "+375291234567", "Toyota Camry");

        Set<ConstraintViolation<ClientRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void clientRequest_validData_hasNoViolations() {
        ClientRequest request = new ClientRequest("Ivan Ivanov", "+375291234567", "Toyota Camry");

        Set<ConstraintViolation<ClientRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void serviceRequest_negativePrice_isRejected() {
        ServiceRequest request = new ServiceRequest("Wash", "Body wash", new BigDecimal("-10.00"), 30);

        Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void appointmentRequest_pastDateTime_isRejected() {
        AppointmentRequest request = new AppointmentRequest(1L, 1L, LocalDateTime.now().minusDays(1));

        Set<ConstraintViolation<AppointmentRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void registerRequest_shortPassword_isRejected() {
        RegisterRequest request = new RegisterRequest("bob", "123");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
    }
}
