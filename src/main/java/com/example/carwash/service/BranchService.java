package com.example.carwash.service;

import com.example.carwash.dto.request.BranchRequest;
import com.example.carwash.dto.response.BranchResponse;
import com.example.carwash.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Manages car wash branches. Soft-deleted branches are excluded from every
 * method here — deletion never removes a row, it only flips its
 * {@code deleted} flag.
 */
public interface BranchService {

    /**
     * @return all non-deleted branches
     */
    List<BranchResponse> getAll();

    /**
     * @param id the branch id
     * @return the matching branch
     * @throws ResourceNotFoundException if no non-deleted branch has this id
     */
    BranchResponse getById(Long id);

    /**
     * @param request the new branch's data
     * @return the created branch, with its generated id
     */
    BranchResponse create(BranchRequest request);

    /**
     * Overwrites an existing branch's fields with the given request.
     *
     * @param id      the branch id
     * @param request the new values to apply
     * @return the updated branch
     * @throws ResourceNotFoundException if no non-deleted branch has this id
     */
    BranchResponse update(Long id, BranchRequest request);

    /**
     * Marks a branch as deleted without removing its row.
     *
     * @param id the branch id
     * @throws ResourceNotFoundException if no non-deleted branch has this id
     */
    void softDelete(Long id);
}
