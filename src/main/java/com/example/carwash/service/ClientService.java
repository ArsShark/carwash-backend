package com.example.carwash.service;

import com.example.carwash.dto.request.ClientRequest;
import com.example.carwash.dto.response.ClientResponse;
import com.example.carwash.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Manages car wash clients. Soft-deleted clients are excluded from every
 * method here — deletion never removes a row, it only flips its
 * {@code deleted} flag.
 */
public interface ClientService {

    /**
     * @return all non-deleted clients
     */
    List<ClientResponse> getAll();

    /**
     * @param id the client id
     * @return the matching client
     * @throws ResourceNotFoundException if no non-deleted client has this id
     */
    ClientResponse getById(Long id);

    /**
     * @param request the new client's data
     * @return the created client, with its generated id
     */
    ClientResponse create(ClientRequest request);

    /**
     * Overwrites an existing client's fields with the given request.
     *
     * @param id      the client id
     * @param request the new values to apply
     * @return the updated client
     * @throws ResourceNotFoundException if no non-deleted client has this id
     */
    ClientResponse update(Long id, ClientRequest request);

    /**
     * Marks a client as deleted without removing its row.
     *
     * @param id the client id
     * @throws ResourceNotFoundException if no non-deleted client has this id
     */
    void softDelete(Long id);
}
