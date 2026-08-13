package com.billing.dto;

import java.util.List;
import lombok.Data;

@Data
public class PatientDto {
    
     private long patientId;
    private String name;
    private String gender;
    private Integer age;
    private List<String> symptoms;
    private String contactNumber;
    private Long userId;
}
