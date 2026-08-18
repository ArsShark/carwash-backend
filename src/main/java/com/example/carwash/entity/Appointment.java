package com.example.carwash.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

/**
 * A booking linking a {@link Client} to a {@link ServiceEntity} at a given date and time.
 *
 * <p>Soft-deleted rows ({@code deleted = true}) are excluded from every
 * query automatically via {@link SQLRestriction}, so deletion never
 * removes a row from the database.
 */
@Entity
@Table(name = "appointments")
@SQLRestriction("deleted = false")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointments_client"))
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointments_service"))
    private ServiceEntity service;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AppointmentStatus status = AppointmentStatus.BOOKED;

    @Column(nullable = false)
    private Boolean deleted = false;
}