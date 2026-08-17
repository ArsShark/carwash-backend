package com.example.carwash.repository;

import com.example.carwash.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Spring Data will generate the query honoring the deleted = false filter automatically
    List<Appointment> findByClientId(Long clientId);
}