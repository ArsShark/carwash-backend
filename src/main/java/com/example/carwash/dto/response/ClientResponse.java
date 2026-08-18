package com.example.carwash.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * A client as returned to API callers.
 */
public class ClientResponse {
    private Long id;
    private String fullName;
    private String phone;
    private String carModel;
    // The deleted field is intentionally not exposed here.
}