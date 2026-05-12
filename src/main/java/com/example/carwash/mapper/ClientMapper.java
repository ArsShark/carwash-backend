package com.example.carwash.mapper;

import com.example.carwash.dto.request.ClientRequest;
import com.example.carwash.dto.response.ClientResponse;
import com.example.carwash.entity.Client;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    // Из DTO (запрос) в Entity (для сохранения в БД)
    public Client toEntity(ClientRequest request) {
        if (request == null) return null;
        Client client = new Client();
        client.setFullName(request.getFullName());
        client.setPhone(request.getPhone());
        client.setCarModel(request.getCarModel());
        return client;
    }

    // Из Entity (из БД) в DTO (для ответа клиенту)
    public ClientResponse toResponse(Client client) {
        if (client == null) return null;
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setFullName(client.getFullName());
        response.setPhone(client.getPhone());
        response.setCarModel(client.getCarModel());
        return response;
    }

    // Преобразование списка
    public List<ClientResponse> toResponseList(List<Client> clients) {
        return clients.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}