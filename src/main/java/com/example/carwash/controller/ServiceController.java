package com.example.carwash.controller;

import com.example.carwash.dto.request.ServiceRequest;
import com.example.carwash.dto.response.ServiceResponse;
import com.example.carwash.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints for the car wash services offered. Reading is open to any
 * authenticated user ({@code USER} or {@code ADMIN}); creating, updating,
 * and deleting are restricted to {@code ADMIN}.
 */
@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Tag(name = "Services", description = "Car wash service management")
public class ServiceController {

    private final ServiceService serviceService;

    @Operation(summary = "Get all services")
    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAll() {
        return ResponseEntity.ok(serviceService.getAll());
    }

    @Operation(summary = "Get a service by id")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getById(id));
    }

    @Operation(summary = "Create a new service")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceResponse> create(@Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.ok(serviceService.create(request));
    }

    @Operation(summary = "Update an existing service")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceResponse> update(@PathVariable Long id, @Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.ok(serviceService.update(id, request));
    }

    @Operation(summary = "Soft-delete a service")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}
