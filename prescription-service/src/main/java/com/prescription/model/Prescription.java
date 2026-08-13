package com.prescription.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Prescription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PrescriptionMedicine> medicines;
    
    private LocalDate nextVistDate;
    private Long userId;
    private Long doctorId;
    private Long patientId;
    private Long appointmentId;
}
