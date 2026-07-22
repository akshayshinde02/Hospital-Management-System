package com.patient.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "name not be null")
    private String name;

    @NotNull(message = "gender not be null")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Min(1)
    @Max(100)
    private Integer age;

    @NotEmpty(message = "symptoms not be empty")
    @ElementCollection
    private List<String> symptoms;

    private String contactNumber;

    private Long userId;
    
}
