package com.example.carwash.mapper;

import com.example.carwash.dto.request.AppointmentRequest;
import com.example.carwash.dto.response.AppointmentResponse;
import com.example.carwash.entity.Appointment;
import com.example.carwash.entity.Client;
import com.example.carwash.entity.ServiceEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentMapper {

    // On creation we only have the client and service IDs.
    // We build "empty" reference objects with these IDs so JPA understands the association.
    public Appointment toEntity(AppointmentRequest request) {
        if (request == null) return null;
        Appointment appointment = new Appointment();

        // Build references to existing rows by ID
        Client clientRef = new Client();
        clientRef.setId(request.getClientId());
        appointment.setClient(clientRef);

        ServiceEntity serviceRef = new ServiceEntity();
        serviceRef.setId(request.getServiceId());
        appointment.setService(serviceRef);

        appointment.setDateTime(request.getDateTime());
        appointment.setStatus("BOOKED"); // Default status
        return appointment;
    }

    // Apply new date/time onto an existing managed appointment (client/service re-linking is handled in the service layer)
    public void updateEntity(Appointment appointment, AppointmentRequest request) {
        appointment.setDateTime(request.getDateTime());
    }

    // In the response we want to show readable names instead of raw IDs.
    public AppointmentResponse toResponse(Appointment appointment) {
        if (appointment == null) return null;
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setDateTime(appointment.getDateTime());
        response.setStatus(appointment.getStatus());

        // Safely resolve the client's name
        if (appointment.getClient() != null) {
            response.setClientName(appointment.getClient().getFullName());
        }

        // Safely resolve the service's name
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