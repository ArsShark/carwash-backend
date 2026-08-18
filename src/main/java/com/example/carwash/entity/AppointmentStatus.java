package com.example.carwash.entity;

/**
 * Lifecycle states of an {@link Appointment}. New appointments start as
 * {@link #BOOKED}; {@link #COMPLETED} and {@link #CANCELLED} are terminal
 * states.
 */
public enum AppointmentStatus {
    BOOKED,
    COMPLETED,
    CANCELLED
}
