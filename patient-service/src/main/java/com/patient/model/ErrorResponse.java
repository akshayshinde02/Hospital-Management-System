package com.patient.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    
    private String message;
    private String error;
    private LocalDateTime dateTime;
}
