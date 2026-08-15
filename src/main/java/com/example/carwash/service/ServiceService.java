package com.example.carwash.service;

import com.example.carwash.dto.request.ServiceRequest;
import com.example.carwash.dto.response.ServiceResponse;

import java.util.List;

public interface ServiceService {
    List<ServiceResponse> getAll();
    ServiceResponse getById(Long id);
    ServiceResponse create(ServiceRequest request);
    ServiceResponse update(Long id, ServiceRequest request);
    void softDelete(Long id);
}