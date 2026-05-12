package com.example.carwash.service;

import com.example.carwash.dto.request.BranchRequest;
import com.example.carwash.dto.response.BranchResponse;

import java.util.List;

public interface BranchService {
    List<BranchResponse> getAll();
    BranchResponse getById(Long id);
    BranchResponse create(BranchRequest request);
    void softDelete(Long id);
}