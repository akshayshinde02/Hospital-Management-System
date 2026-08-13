package com.prescription.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class AppointmentDto {
    
    private Long appointmentId;
    private Long doctorId;
    private Long patientId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
}
