package com.example.carwash.mapper;

import com.example.carwash.dto.request.BranchRequest;
import com.example.carwash.dto.response.BranchResponse;
import com.example.carwash.entity.Branch;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BranchMapper {

    public Branch toEntity(BranchRequest request) {
        if (request == null) return null;
        Branch branch = new Branch();
        branch.setName(request.getName());
        branch.setAddress(request.getAddress());
        branch.setPhone(request.getPhone());
        return branch;
    }

    public void updateEntity(Branch branch, BranchRequest request) {
        branch.setName(request.getName());
        branch.setAddress(request.getAddress());
        branch.setPhone(request.getPhone());
    }

    public BranchResponse toResponse(Branch branch) {
        if (branch == null) return null;
        BranchResponse response = new BranchResponse();
        response.setId(branch.getId());
        response.setName(branch.getName());
        response.setAddress(branch.getAddress());
        response.setPhone(branch.getPhone());
        return response;
    }

    public List<BranchResponse> toResponseList(List<Branch> branches) {
        return branches.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}