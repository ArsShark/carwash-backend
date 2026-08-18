package com.example.carwash.controller;

import com.example.carwash.dto.request.AppointmentRequest;
import com.example.carwash.dto.response.AppointmentResponse;
import com.example.carwash.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints for appointments. Reading and booking are open to any
 * authenticated user ({@code USER} or {@code ADMIN}); updating an
 * appointment is treated the same as booking one, while cancelling
 * (soft-deleting) it is restricted to {@code ADMIN}.
 */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Car wash appointment management")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "Get all appointments")
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAll() {
        return ResponseEntity.ok(appointmentService.getAll());
    }

    @Operation(summary = "Get an appointment by id")
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getById(id));
    }

    @Operation(summary = "Get appointments for a specific client")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<AppointmentResponse>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(appointmentService.getByClientId(clientId));
    }

    @Operation(summary = "Create a new appointment")
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.create(request));
    }

    @Operation(summary = "Update an existing appointment")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<AppointmentResponse> update(@PathVariable Long id, @Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.update(id, request));
    }

    @Operation(summary = "Soft-delete an appointment")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}
