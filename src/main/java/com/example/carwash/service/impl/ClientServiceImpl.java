package com.example.carwash.service.impl;

import com.example.carwash.dto.request.ClientRequest;
import com.example.carwash.dto.response.ClientResponse;
import com.example.carwash.entity.Client;
import com.example.carwash.exception.ResourceNotFoundException;
import com.example.carwash.mapper.ClientMapper;
import com.example.carwash.repository.ClientRepository;
import com.example.carwash.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;

    @Override
    public List<ClientResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    public ClientResponse getById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return mapper.toResponse(client);
    }

    @Override
    @Transactional
    public ClientResponse create(ClientRequest request) {
        Client client = mapper.toEntity(request);
        Client savedClient = repository.save(client);
        return mapper.toResponse(savedClient);
    }

    @Override
    @Transactional
    public ClientResponse update(Long id, ClientRequest request) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        mapper.updateEntity(client, request);
        Client savedClient = repository.save(client);
        return mapper.toResponse(savedClient);
    }

    @Override
    @Transactional
    public void softDelete(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        client.setDeleted(true);
        repository.save(client);
    }
}