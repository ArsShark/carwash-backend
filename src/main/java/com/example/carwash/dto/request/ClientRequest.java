package com.example.carwash.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9+()\\s-]{6,20}$", message = "Phone must be a valid phone number")
    private String phone;

    @Size(max = 100, message = "Car model must not exceed 100 characters")
    private String carModel;
}
