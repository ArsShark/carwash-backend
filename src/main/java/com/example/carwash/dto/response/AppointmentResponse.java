package com.example.carwash.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.carwash.entity.AppointmentStatus;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * An appointment as returned to API callers, with the client/service
 * names resolved instead of raw ids.
 */
public class AppointmentResponse {
    private Long id;
    private String clientName; // Client name
    private String serviceName; // Service name
    private LocalDateTime dateTime;
    private AppointmentStatus status;
}