package com.example.carwash.service;

import com.example.carwash.dto.request.BranchRequest;
import com.example.carwash.dto.response.BranchResponse;
import com.example.carwash.entity.Branch;
import com.example.carwash.exception.ResourceNotFoundException;
import com.example.carwash.mapper.BranchMapper;
import com.example.carwash.repository.BranchRepository;
import com.example.carwash.service.impl.BranchServiceImpl;
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
class BranchServiceImplTest {

    @Mock
    private BranchRepository repository;

    @Mock
    private BranchMapper mapper;

    @InjectMocks
    private BranchServiceImpl branchService;

    private Branch branch;
    private BranchRequest request;
    private BranchResponse response;

    @BeforeEach
    void setUp() {
        branch = new Branch();
        branch.setId(1L);
        branch.setName("Central");
        branch.setAddress("Minsk, Lenina 1");
        branch.setPhone("+375291234567");
        branch.setDeleted(false);

        request = new BranchRequest("Central", "Minsk, Lenina 1", "+375291234567");

        response = new BranchResponse();
        response.setId(1L);
        response.setName("Central");
        response.setAddress("Minsk, Lenina 1");
        response.setPhone("+375291234567");
    }

    @Test
    void getAll_returnsMappedList() {
        when(repository.findAll()).thenReturn(List.of(branch));
        when(mapper.toResponseList(List.of(branch))).thenReturn(List.of(response));

        List<BranchResponse> result = branchService.getAll();

        assertThat(result).hasSize(1);
        verify(repository).findAll();
    }

    @Test
    void getById_whenNotFound_throwsResourceNotFoundException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> branchService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_savesAndReturnsMappedResponse() {
        when(mapper.toEntity(request)).thenReturn(branch);
        when(repository.save(branch)).thenReturn(branch);
        when(mapper.toResponse(branch)).thenReturn(response);

        BranchResponse result = branchService.create(request);

        assertThat(result.getName()).isEqualTo("Central");
        verify(repository).save(branch);
    }

    @Test
    void update_whenFound_appliesChangesAndSaves() {
        when(repository.findById(1L)).thenReturn(Optional.of(branch));
        when(repository.save(branch)).thenReturn(branch);
        when(mapper.toResponse(branch)).thenReturn(response);

        branchService.update(1L, request);

        verify(mapper).updateEntity(branch, request);
        verify(repository).save(branch);
    }

    @Test
    void update_whenNotFound_throwsResourceNotFoundException() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> branchService.update(42L, request))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).save(any());
    }

    @Test
    void softDelete_whenFound_marksAsDeletedAndSaves() {
        when(repository.findById(1L)).thenReturn(Optional.of(branch));

        branchService.softDelete(1L);

        assertThat(branch.getDeleted()).isTrue();
        verify(repository).save(branch);
    }

    @Test
    void softDelete_whenNotFound_throwsResourceNotFoundException() {
        when(repository.findById(7L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> branchService.softDelete(7L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).save(any());
    }
}
