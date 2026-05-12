package com.example.carwash.service;

import com.example.carwash.dto.request.ClientRequest;
import com.example.carwash.dto.response.ClientResponse;

import java.util.List;

public interface ClientService {
    List<ClientResponse> getAll();
    ClientResponse getById(Long id);
    ClientResponse create(ClientRequest request);
    void softDelete(Long id);
}