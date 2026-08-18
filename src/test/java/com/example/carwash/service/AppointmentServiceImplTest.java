package com.example.carwash.service;

import com.example.carwash.dto.request.AppointmentRequest;
import com.example.carwash.dto.response.AppointmentResponse;
import com.example.carwash.entity.Appointment;
import com.example.carwash.entity.AppointmentStatus;
import com.example.carwash.entity.Client;
import com.example.carwash.entity.ServiceEntity;
import com.example.carwash.exception.ResourceNotFoundException;
import com.example.carwash.mapper.AppointmentMapper;
import com.example.carwash.repository.AppointmentRepository;
import com.example.carwash.repository.ClientRepository;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository repository;

    @Mock
    private AppointmentMapper mapper;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private Appointment appointment;
    private Client client;
    private ServiceEntity serviceEntity;
    private AppointmentRequest request;
    private AppointmentResponse response;
    private LocalDateTime futureDateTime;

    @BeforeEach
    void setUp() {
        futureDateTime = LocalDateTime.now().plusDays(1);

        client = new Client();
        client.setId(1L);
        client.setFullName("Ivan Ivanov");

        serviceEntity = new ServiceEntity();
        serviceEntity.setId(1L);
        serviceEntity.setName("Body wash");

        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setClient(client);
        appointment.setService(serviceEntity);
        appointment.setDateTime(futureDateTime);
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setDeleted(false);

        request = new AppointmentRequest(1L, 1L, futureDateTime);

        response = new AppointmentResponse();
        response.setId(1L);
        response.setClientName("Ivan Ivanov");
        response.setServiceName("Body wash");
        response.setDateTime(futureDateTime);
        response.setStatus(AppointmentStatus.BOOKED);
    }

    @Test
    void create_whenClientAndServiceExist_savesAppointment() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(serviceEntity));
        when(mapper.toEntity(request)).thenReturn(appointment);
        when(repository.save(appointment)).thenReturn(appointment);
        when(mapper.toResponse(appointment)).thenReturn(response);

        AppointmentResponse result = appointmentService.create(request);

        assertThat(result.getClientName()).isEqualTo("Ivan Ivanov");
        assertThat(appointment.getClient()).isEqualTo(client);
        assertThat(appointment.getService()).isEqualTo(serviceEntity);
        verify(repository).save(appointment);
    }

    @Test
    void create_whenClientMissing_throwsResourceNotFoundException() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Client not found");

        verify(repository, never()).save(any());
    }

    @Test
    void create_whenServiceMissing_throwsResourceNotFoundException() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Service not found");

        verify(repository, never()).save(any());
    }

    @Test
    void update_whenAppointmentFound_appliesChangesAndSaves() {
        when(repository.findById(1L)).thenReturn(Optional.of(appointment));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(serviceEntity));
        when(repository.save(appointment)).thenReturn(appointment);
        when(mapper.toResponse(appointment)).thenReturn(response);

        appointmentService.update(1L, request);

        verify(mapper).updateEntity(appointment, request);
        verify(repository).save(appointment);
    }

    @Test
    void update_whenAppointmentNotFound_throwsResourceNotFoundException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.update(99L, request))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).save(any());
    }

    @Test
    void getByClientId_returnsMappedAppointments() {
        when(repository.findByClientId(1L)).thenReturn(List.of(appointment));
        when(mapper.toResponseList(List.of(appointment))).thenReturn(List.of(response));

        List<AppointmentResponse> result = appointmentService.getByClientId(1L);

        assertThat(result).hasSize(1);
        verify(repository).findByClientId(1L);
    }

    @Test
    void softDelete_whenFound_marksAsDeletedAndSaves() {
        when(repository.findById(1L)).thenReturn(Optional.of(appointment));

        appointmentService.softDelete(1L);

        assertThat(appointment.getDeleted()).isTrue();
        assertThat(appointment.getStatus()).isEqualTo(AppointmentStatus.CANCELLED);
        verify(repository).save(appointment);
    }

    @Test
    void softDelete_whenNotFound_throwsResourceNotFoundException() {
        when(repository.findById(7L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.softDelete(7L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).save(any());
    }
}
