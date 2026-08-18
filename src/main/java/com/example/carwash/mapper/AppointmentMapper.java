package com.example.carwash.mapper;

import com.example.carwash.dto.request.AppointmentRequest;
import com.example.carwash.dto.response.AppointmentResponse;
import com.example.carwash.entity.Appointment;
import com.example.carwash.entity.Client;
import com.example.carwash.entity.ServiceEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts between {@link Appointment} entities and their request/response
 * DTOs.
 */
@Component
public class AppointmentMapper {

    /**
     * Builds a new appointment from a request. The client and service are
     * set as bare id-only references here — {@code AppointmentServiceImpl}
     * replaces them with the real, fully-loaded entities after verifying
     * they exist, so this method alone does not guarantee a valid
     * association.
     */
    public Appointment toEntity(AppointmentRequest request) {
        if (request == null) return null;
        Appointment appointment = new Appointment();

        Client clientRef = new Client();
        clientRef.setId(request.getClientId());
        appointment.setClient(clientRef);

        ServiceEntity serviceRef = new ServiceEntity();
        serviceRef.setId(request.getServiceId());
        appointment.setService(serviceRef);

        appointment.setDateTime(request.getDateTime());
        // status defaults to BOOKED via the entity's field initializer
        return appointment;
    }

    /**
     * Applies the request's new date/time onto an already-persisted
     * appointment. Client/service re-linking is handled by the caller
     * ({@code AppointmentServiceImpl}), not here.
     */
    public void updateEntity(Appointment appointment, AppointmentRequest request) {
        appointment.setDateTime(request.getDateTime());
    }

    public AppointmentResponse toResponse(Appointment appointment) {
        if (appointment == null) return null;
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setDateTime(appointment.getDateTime());
        response.setStatus(appointment.getStatus());

        if (appointment.getClient() != null) {
            response.setClientName(appointment.getClient().getFullName());
        }

        if (appointment.getService() != null) {
            response.setServiceName(appointment.getService().getName());
        }
        return response;
    }

    public List<AppointmentResponse> toResponseList(List<Appointment> appointments) {
        return appointments.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
