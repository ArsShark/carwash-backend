package com.example.carwash.service;

import com.example.carwash.dto.request.ServiceRequest;
import com.example.carwash.dto.response.ServiceResponse;
import com.example.carwash.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Manages the car wash services offered (e.g. body wash, interior cleaning).
 * Soft-deleted services are excluded from every method here — deletion
 * never removes a row, it only flips its {@code deleted} flag.
 */
public interface ServiceService {

    /**
     * @return all non-deleted services
     */
    List<ServiceResponse> getAll();

    /**
     * @param id the service id
     * @return the matching service
     * @throws ResourceNotFoundException if no non-deleted service has this id
     */
    ServiceResponse getById(Long id);

    /**
     * @param request the new service's data
     * @return the created service, with its generated id
     */
    ServiceResponse create(ServiceRequest request);

    /**
     * Overwrites an existing service's fields with the given request.
     *
     * @param id      the service id
     * @param request the new values to apply
     * @return the updated service
     * @throws ResourceNotFoundException if no non-deleted service has this id
     */
    ServiceResponse update(Long id, ServiceRequest request);

    /**
     * Marks a service as deleted without removing its row.
     *
     * @param id the service id
     * @throws ResourceNotFoundException if no non-deleted service has this id
     */
    void softDelete(Long id);
}
