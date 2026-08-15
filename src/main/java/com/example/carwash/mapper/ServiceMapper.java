package com.example.carwash.mapper;

import com.example.carwash.dto.request.ServiceRequest;
import com.example.carwash.dto.response.ServiceResponse;
import com.example.carwash.entity.ServiceEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceMapper {

    public ServiceEntity toEntity(ServiceRequest request) {
        if (request == null) return null;
        ServiceEntity entity = new ServiceEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setDurationMinutes(request.getDurationMinutes());
        return entity;
    }

    public void updateEntity(ServiceEntity entity, ServiceRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setDurationMinutes(request.getDurationMinutes());
    }

    public ServiceResponse toResponse(ServiceEntity entity) {
        if (entity == null) return null;
        ServiceResponse response = new ServiceResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setPrice(entity.getPrice());
        return response;
    }

    public List<ServiceResponse> toResponseList(List<ServiceEntity> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}