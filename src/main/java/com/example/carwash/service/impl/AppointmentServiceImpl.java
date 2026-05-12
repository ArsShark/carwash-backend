package com.example.carwash.service.impl;

import com.example.carwash.dto.request.AppointmentRequest;
import com.example.carwash.dto.response.AppointmentResponse;
import com.example.carwash.entity.Appointment;
import com.example.carwash.entity.Client;
import com.example.carwash.entity.ServiceEntity;
import com.example.carwash.exception.ResourceNotFoundException;
import com.example.carwash.mapper.AppointmentMapper;
import com.example.carwash.repository.AppointmentRepository;
import com.example.carwash.repository.ClientRepository;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public List<AppointmentResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    public AppointmentResponse getById(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        return mapper.toResponse(appointment);
    }

    @Override
    public List<AppointmentResponse> getByClientId(Long clientId) {
        return mapper.toResponseList(repository.findByClientId(clientId));
    }

    @Override
    @Transactional
    public AppointmentResponse create(AppointmentRequest request) {
        // Проверяем существование клиента и услуги
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + request.getClientId()));

        ServiceEntity service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + request.getServiceId()));

        Appointment appointment = mapper.toEntity(request);
        appointment.setClient(client);
        appointment.setService(service);

        Appointment savedAppointment = repository.save(appointment);
        return mapper.toResponse(savedAppointment);
    }

    @Override
    @Transactional
    public void softDelete(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        appointment.setDeleted(true);
        repository.save(appointment);
    }
}