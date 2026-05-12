package com.example.carwash.service;

import com.example.carwash.dto.request.AppointmentRequest;
import com.example.carwash.dto.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    List<AppointmentResponse> getAll();
    AppointmentResponse getById(Long id);
    List<AppointmentResponse> getByClientId(Long clientId);
    AppointmentResponse create(AppointmentRequest request);
    void softDelete(Long id);
}