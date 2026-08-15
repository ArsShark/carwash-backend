package com.example.carwash.service;

import com.example.carwash.dto.request.ClientRequest;
import com.example.carwash.dto.response.ClientResponse;
import com.example.carwash.entity.Client;
import com.example.carwash.exception.ResourceNotFoundException;
import com.example.carwash.mapper.ClientMapper;
import com.example.carwash.repository.ClientRepository;
import com.example.carwash.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository repository;

    @Mock
    private ClientMapper mapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private ClientRequest request;
    private ClientResponse response;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setFullName("Ivan Ivanov");
        client.setPhone("+7 (999) 123-45-67");
        client.setCarModel("Toyota Camry");
        client.setDeleted(false);

        request = new ClientRequest("Ivan Ivanov", "+7 (999) 123-45-67", "Toyota Camry");

        response = new ClientResponse();
        response.setId(1L);
        response.setFullName("Ivan Ivanov");
        response.setPhone("+7 (999) 123-45-67");
        response.setCarModel("Toyota Camry");
    }

    @Test
    void getAll_returnsMappedList() {
        when(repository.findAll()).thenReturn(List.of(client));
        when(mapper.toResponseList(List.of(client))).thenReturn(List.of(response));

        List<ClientResponse> result = clientService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFullName()).isEqualTo("Ivan Ivanov");
        verify(repository).findAll();
    }

    @Test
    void getById_whenFound_returnsClient() {
        when(repository.findById(1L)).thenReturn(Optional.of(client));
        when(mapper.toResponse(client)).thenReturn(response);

        ClientResponse result = clientService.getById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getById_whenNotFound_throwsResourceNotFoundException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void create_savesAndReturnsMappedResponse() {
        when(mapper.toEntity(request)).thenReturn(client);
        when(repository.save(client)).thenReturn(client);
        when(mapper.toResponse(client)).thenReturn(response);

        ClientResponse result = clientService.create(request);

        assertThat(result.getFullName()).isEqualTo("Ivan Ivanov");
        verify(repository).save(client);
    }

    @Test
    void update_whenFound_appliesChangesAndSaves() {
        when(repository.findById(1L)).thenReturn(Optional.of(client));
        when(repository.save(client)).thenReturn(client);
        when(mapper.toResponse(client)).thenReturn(response);

        ClientResponse result = clientService.update(1L, request);

        verify(mapper).updateEntity(client, request);
        verify(repository).save(client);
        assertThat(result.getFullName()).isEqualTo("Ivan Ivanov");
    }

    @Test
    void update_whenNotFound_throwsResourceNotFoundException() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.update(42L, request))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).save(any());
    }

    @Test
    void softDelete_whenFound_marksAsDeletedAndSaves() {
        when(repository.findById(1L)).thenReturn(Optional.of(client));

        clientService.softDelete(1L);

        assertThat(client.getDeleted()).isTrue();
        verify(repository).save(client);
    }

    @Test
    void softDelete_whenNotFound_throwsResourceNotFoundException() {
        when(repository.findById(7L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.softDelete(7L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).save(any());
    }
}
