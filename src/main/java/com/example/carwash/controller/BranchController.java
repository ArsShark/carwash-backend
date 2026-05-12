package com.example.carwash.controller;

import com.example.carwash.dto.request.BranchRequest;
import com.example.carwash.dto.response.BranchResponse;
import com.example.carwash.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
@Tag(name = "Branches", description = "Управление филиалами автомойки")
public class BranchController {

    private final BranchService branchService;

    @Operation(summary = "Получить все филиалы")
    @GetMapping
    public ResponseEntity<List<BranchResponse>> getAll() {
        return ResponseEntity.ok(branchService.getAll());
    }

    @Operation(summary = "Получить филиал по ID")
    @GetMapping("/{id}")
    public ResponseEntity<BranchResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getById(id));
    }

    @Operation(summary = "Создать новый филиал")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BranchResponse> create(@RequestBody BranchRequest request) {
        return ResponseEntity.ok(branchService.create(request));
    }

    @Operation(summary = "Мягкое удаление филиала (Soft Delete)")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        branchService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}