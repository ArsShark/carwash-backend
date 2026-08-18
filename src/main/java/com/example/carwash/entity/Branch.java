package com.example.carwash.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

/**
 * A car wash branch/location.
 *
 * <p>Soft-deleted rows ({@code deleted = true}) are excluded from every
 * query automatically via {@link SQLRestriction}, so deletion never
 * removes a row from the database.
 */
@Entity
@Table(name = "branches")
@SQLRestriction("deleted = false")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String address;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false)
    private Boolean deleted = false;
}