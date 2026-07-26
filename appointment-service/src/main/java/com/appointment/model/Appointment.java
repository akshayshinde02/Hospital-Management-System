package com.appointment.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "appointment")
@Data
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    private Long userId;
    private Long doctorId;
    private Long patientId;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}
