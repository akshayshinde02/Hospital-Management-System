package com.billing.dto;

import java.util.List;

import lombok.Data;

@Data
public class DoctorDto {
    private Long doctorId;

    private String doctorName;
    private String doctorAge;
    private List<String> experties;
    private String department;
    private String qualification;
    private Long userId;
}
