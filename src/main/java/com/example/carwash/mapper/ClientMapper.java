package com.example.carwash.mapper;

import com.example.carwash.dto.request.ClientRequest;
import com.example.carwash.dto.response.ClientResponse;
import com.example.carwash.entity.Client;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts between {@link Client} entities and their request/response DTOs.
 */
@Component
public class ClientMapper {

    public Client toEntity(ClientRequest request) {
        if (request == null) return null;
        Client client = new Client();
        client.setFullName(request.getFullName());
        client.setPhone(request.getPhone());
        client.setCarModel(request.getCarModel());
        return client;
    }

    /**
     * Applies the request's fields onto an already-persisted entity, for
     * updates. Leaves {@code id} and {@code deleted} untouched.
     */
    public void updateEntity(Client client, ClientRequest request) {
        client.setFullName(request.getFullName());
        client.setPhone(request.getPhone());
        client.setCarModel(request.getCarModel());
    }

    public ClientResponse toResponse(Client client) {
        if (client == null) return null;
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setFullName(client.getFullName());
        response.setPhone(client.getPhone());
        response.setCarModel(client.getCarModel());
        return response;
    }

    public List<ClientResponse> toResponseList(List<Client> clients) {
        return clients.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
