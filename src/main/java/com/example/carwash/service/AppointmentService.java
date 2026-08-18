package com.example.carwash.service;

import com.example.carwash.dto.request.AppointmentRequest;
import com.example.carwash.dto.response.AppointmentResponse;
import com.example.carwash.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Manages car wash appointments, linking a client to a service at a given
 * date and time. Soft-deleted appointments are excluded from every method
 * here — deletion never removes a row, it only flips its {@code deleted}
 * flag.
 */
public interface AppointmentService {

    /**
     * @return all non-deleted appointments
     */
    List<AppointmentResponse> getAll();

    /**
     * @param id the appointment id
     * @return the matching appointment
     * @throws ResourceNotFoundException if no non-deleted appointment has this id
     */
    AppointmentResponse getById(Long id);

    /**
     * @param clientId the client id
     * @return all non-deleted appointments booked by this client
     */
    List<AppointmentResponse> getByClientId(Long clientId);

    /**
     * Books a new appointment. Defaults to {@link com.example.carwash.entity.AppointmentStatus#BOOKED}.
     *
     * @param request the client, service and date/time for the new appointment
     * @return the created appointment, with its generated id
     * @throws ResourceNotFoundException if the referenced client or service does not exist
     */
    AppointmentResponse create(AppointmentRequest request);

    /**
     * Overwrites an existing appointment's client, service and date/time.
     *
     * @param id      the appointment id
     * @param request the new values to apply
     * @return the updated appointment
     * @throws ResourceNotFoundException if the appointment, the referenced client,
     *                                    or the referenced service does not exist
     */
    AppointmentResponse update(Long id, AppointmentRequest request);

    /**
     * Marks an appointment as deleted without removing its row, and sets
     * its status to {@link com.example.carwash.entity.AppointmentStatus#CANCELLED}.
     *
     * @param id the appointment id
     * @throws ResourceNotFoundException if no non-deleted appointment has this id
     */
    void softDelete(Long id);
}
