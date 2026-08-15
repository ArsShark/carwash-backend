package com.example.carwash.service;

import com.example.carwash.dto.request.ServiceRequest;
import com.example.carwash.dto.response.ServiceResponse;
import com.example.carwash.entity.ServiceEntity;
import com.example.carwash.exception.ResourceNotFoundException;
import com.example.carwash.mapper.ServiceMapper;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.service.impl.ServiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceServiceImplTest {

    @Mock
    private ServiceRepository repository;

    @Mock
    private ServiceMapper mapper;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    private ServiceEntity entity;
    private ServiceRequest request;
    private ServiceResponse response;

    @BeforeEach
    void setUp() {
        entity = new ServiceEntity();
        entity.setId(1L);
        entity.setName("Body wash");
        entity.setDescription("Foam body wash");
        entity.setPrice(new BigDecimal("500.00"));
        entity.setDurationMinutes(30);
        entity.setDeleted(false);

        request = new ServiceRequest("Body wash", "Foam body wash", new BigDecimal("500.00"), 30);

        response = new ServiceResponse();
        response.setId(1L);
        response.setName("Body wash");
        response.setDescription("Foam body wash");
        response.setPrice(new BigDecimal("500.00"));
    }

    @Test
    void getAll_returnsMappedList() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponseList(List.of(entity))).thenReturn(List.of(response));

        List<ServiceResponse> result = serviceService.getAll();

        assertThat(result).hasSize(1);
        verify(repository).findAll();
    }

    @Test
    void getById_whenNotFound_throwsResourceNotFoundException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> serviceService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_savesAndReturnsMappedResponse() {
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        ServiceResponse result = serviceService.create(request);

        assertThat(result.getName()).isEqualTo("Body wash");
        verify(repository).save(entity);
    }

    @Test
    void update_whenFound_appliesChangesAndSaves() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        serviceService.update(1L, request);

        verify(mapper).updateEntity(entity, request);
        verify(repository).save(entity);
    }

    @Test
    void update_whenNotFound_throwsResourceNotFoundException() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> serviceService.update(42L, request))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).save(any());
    }

    @Test
    void softDelete_whenFound_marksAsDeletedAndSaves() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        serviceService.softDelete(1L);

        assertThat(entity.getDeleted()).isTrue();
        verify(repository).save(entity);
    }

    @Test
    void softDelete_whenNotFound_throwsResourceNotFoundException() {
        when(repository.findById(7L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> serviceService.softDelete(7L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).save(any());
    }
}
