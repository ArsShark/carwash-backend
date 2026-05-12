package com.example.carwash.service.impl;

import com.example.carwash.dto.request.BranchRequest;
import com.example.carwash.dto.response.BranchResponse;
import com.example.carwash.entity.Branch;
import com.example.carwash.exception.ResourceNotFoundException;
import com.example.carwash.mapper.BranchMapper;
import com.example.carwash.repository.BranchRepository;
import com.example.carwash.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository repository;
    private final BranchMapper mapper;

    @Override
    public List<BranchResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    public BranchResponse getById(Long id) {
        Branch branch = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + id));
        return mapper.toResponse(branch);
    }

    @Override
    @Transactional
    public BranchResponse create(BranchRequest request) {
        Branch branch = mapper.toEntity(request);
        Branch savedBranch = repository.save(branch);
        return mapper.toResponse(savedBranch);
    }

    @Override
    @Transactional
    public void softDelete(Long id) {
        Branch branch = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + id));
        branch.setDeleted(true);
        repository.save(branch);
    }
}