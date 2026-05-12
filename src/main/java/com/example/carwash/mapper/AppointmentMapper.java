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

    // При создании записи у нас есть только ID клиента и услуги.
    // Мы создаем "пустые" объекты-ссылки с этими ID, чтобы JPA понял связь.
    public Appointment toEntity(AppointmentRequest request) {
        if (request == null) return null;
        Appointment appointment = new Appointment();

        // Создаем ссылки на существующие записи в БД по ID
        Client clientRef = new Client();
        clientRef.setId(request.getClientId());
        appointment.setClient(clientRef);

        ServiceEntity serviceRef = new ServiceEntity();
        serviceRef.setId(request.getServiceId());
        appointment.setService(serviceRef);

        appointment.setDateTime(request.getDateTime());
        appointment.setStatus("BOOKED"); // Статус по умолчанию
        return appointment;
    }

    // При ответе мы хотим показать красивые имена, а не ID объектов.
    public AppointmentResponse toResponse(Appointment appointment) {
        if (appointment == null) return null;
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setDateTime(appointment.getDateTime());
        response.setStatus(appointment.getStatus());

        // Безопасное получение имени клиента
        if (appointment.getClient() != null) {
            response.setClientName(appointment.getClient().getFullName());
        }

        // Безопасное получение названия услуги
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