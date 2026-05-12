package com.example.carwash.service.impl;

import com.example.carwash.dto.request.ServiceRequest;
import com.example.carwash.dto.response.ServiceResponse;
import com.example.carwash.entity.ServiceEntity;
import com.example.carwash.exception.ResourceNotFoundException;
import com.example.carwash.mapper.ServiceMapper;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository repository;
    private final ServiceMapper mapper;

    @Override
    public List<ServiceResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    public ServiceResponse getById(Long id) {
        ServiceEntity serviceEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        return mapper.toResponse(serviceEntity);
    }

    @Override
    @Transactional
    public ServiceResponse create(ServiceRequest request) {
        ServiceEntity serviceEntity = mapper.toEntity(request);
        ServiceEntity savedService = repository.save(serviceEntity);
        return mapper.toResponse(savedService);
    }

    @Override
    @Transactional
    public void softDelete(Long id) {
        ServiceEntity serviceEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        serviceEntity.setDeleted(true);
        repository.save(serviceEntity);
    }
}