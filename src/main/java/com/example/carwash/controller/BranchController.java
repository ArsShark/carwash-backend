package com.example.carwash.controller;

import com.example.carwash.dto.request.BranchRequest;
import com.example.carwash.dto.response.BranchResponse;
import com.example.carwash.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints for car wash branches. Reading is open to any
 * authenticated user ({@code USER} or {@code ADMIN}); creating, updating,
 * and deleting are restricted to {@code ADMIN}.
 */
@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
@Tag(name = "Branches", description = "Car wash branch management")
public class BranchController {

    private final BranchService branchService;

    @Operation(summary = "Get all branches")
    @GetMapping
    public ResponseEntity<List<BranchResponse>> getAll() {
        return ResponseEntity.ok(branchService.getAll());
    }

    @Operation(summary = "Get a branch by id")
    @GetMapping("/{id}")
    public ResponseEntity<BranchResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getById(id));
    }

    @Operation(summary = "Create a new branch")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BranchResponse> create(@Valid @RequestBody BranchRequest request) {
        return ResponseEntity.ok(branchService.create(request));
    }

    @Operation(summary = "Update an existing branch")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BranchResponse> update(@PathVariable Long id, @Valid @RequestBody BranchRequest request) {
        return ResponseEntity.ok(branchService.update(id, request));
    }

    @Operation(summary = "Soft-delete a branch")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        branchService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}
