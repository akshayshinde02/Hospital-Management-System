package com.prescription.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.prescription.dto.DoctorDto;

@FeignClient(name = "DOCTOR-SERVICE")
public interface DoctorClient {
    
    @GetMapping("/doctor/getdoctor/{docterId}")
    DoctorDto getDoctor(@RequestHeader("Authorization") String token, @PathVariable Long docterId);
}
