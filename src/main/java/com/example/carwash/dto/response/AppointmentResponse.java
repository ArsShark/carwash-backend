package com.example.carwash.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
    private Long id;
    private String clientName; // Client name
    private String serviceName; // Service name
    private LocalDateTime dateTime;
    private String status;
}