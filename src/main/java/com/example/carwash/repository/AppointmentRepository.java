package com.example.carwash.repository;

import com.example.carwash.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Spring Data автоматически сгенерирует запрос с учетом фильтра deleted = false
    List<Appointment> findByClientId(Long clientId);
}