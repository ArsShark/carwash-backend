package com.example.carwash.entity;

/**
 * The fixed set of roles a {@link User} can hold. Values follow Spring
 * Security's {@code ROLE_} naming convention, which {@code hasRole(...)}
 * checks expect implicitly.
 */
public enum RoleName {
    ROLE_USER,
    ROLE_ADMIN
}
