package com.example.carwash.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/**
 * A service the car wash offers (e.g. body wash, interior cleaning), with its price and typical duration.
 *
 * <p>Soft-deleted rows ({@code deleted = true}) are excluded from every
 * query automatically via {@link SQLRestriction}, so deletion never
 * removes a row from the database.
 */
@Entity
@Table(name = "services")
@SQLRestriction("deleted = false")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(nullable = false)
    private Boolean deleted = false;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Appointment> appointments;
}