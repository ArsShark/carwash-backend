package com.example.carwash.controller;

import com.example.carwash.dto.request.AppointmentRequest;
import com.example.carwash.dto.response.AppointmentResponse;
import com.example.carwash.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Управление записями на мойку")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "Получить все записи")
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAll() {
        return ResponseEntity.ok(appointmentService.getAll());
    }

    @Operation(summary = "Получить запись по ID")
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getById(id));
    }

    @Operation(summary = "Получить записи конкретного клиента")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<AppointmentResponse>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(appointmentService.getByClientId(clientId));
    }

    @Operation(summary = "Создать новую запись на мойку")
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<AppointmentResponse> create(@RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.create(request));
    }

    @Operation(summary = "Мягкое удаление записи (Soft Delete)")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}