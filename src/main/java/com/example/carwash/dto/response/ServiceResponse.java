package com.example.carwash.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * A car wash service as returned to API callers.
 */
public class ServiceResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}