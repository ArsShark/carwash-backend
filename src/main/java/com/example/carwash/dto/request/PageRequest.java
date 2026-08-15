package com.example.carwash.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRequest {
    private int page = 0;
    private int size = 10;
    private String sortBy = "id";
    private String sortDir = "asc";
}