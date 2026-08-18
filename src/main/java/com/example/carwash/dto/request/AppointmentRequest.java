package com.example.carwash.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * Payload for booking or updating an appointment.
 */
public class AppointmentRequest {

    @NotNull(message = "Client id is required")
    private Long clientId;

    @NotNull(message = "Service id is required")
    private Long serviceId;

    @NotNull(message = "Date and time are required")
    @Future(message = "Appointment date and time must be in the future")
    private LocalDateTime dateTime;
}
