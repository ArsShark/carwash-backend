package com.example.carwash.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

/**
 * A car wash client. Soft-deleted rows ({@code deleted = true}) are excluded from
 * every query automatically via {@link SQLRestriction}, so deletion never
 * removes a row from the database.
 */
@Entity
@Table(name = "clients")
@SQLRestriction("deleted = false")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(length = 20)
    private String phone;

    @Column(name = "car_model", length = 100)
    private String carModel;

    @Column(nullable = false)
    private Boolean deleted = false;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Appointment> appointments;
}