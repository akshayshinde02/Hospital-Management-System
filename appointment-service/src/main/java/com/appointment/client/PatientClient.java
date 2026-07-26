package com.appointment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.appointment.dto.PatientDto;

@FeignClient(name = "PATIENT-SERVICE")
public interface PatientClient {
    
    @GetMapping("/patient/get/patient")
    PatientDto getPatient(@RequestHeader("Authorization") String token);
}
