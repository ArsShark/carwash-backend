package com.example.carwash.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * A branch as returned to API callers.
 */
public class BranchResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
}