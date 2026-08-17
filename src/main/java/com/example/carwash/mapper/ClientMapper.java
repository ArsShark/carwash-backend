package com.example.carwash.mapper;

import com.example.carwash.dto.request.ClientRequest;
import com.example.carwash.dto.response.ClientResponse;
import com.example.carwash.entity.Client;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    // From DTO (request) to entity (for persistence)
    public Client toEntity(ClientRequest request) {
        if (request == null) return null;
        Client client = new Client();
        client.setFullName(request.getFullName());
        client.setPhone(request.getPhone());
        client.setCarModel(request.getCarModel());
        return client;
    }

    // Apply request fields onto an existing managed entity (used for updates)
    public void updateEntity(Client client, ClientRequest request) {
        client.setFullName(request.getFullName());
        client.setPhone(request.getPhone());
        client.setCarModel(request.getCarModel());
    }

    // From entity (from DB) to DTO (for the response)
    public ClientResponse toResponse(Client client) {
        if (client == null) return null;
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setFullName(client.getFullName());
        response.setPhone(client.getPhone());
        response.setCarModel(client.getCarModel());
        return response;
    }

    // List conversion
    public List<ClientResponse> toResponseList(List<Client> clients) {
        return clients.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}