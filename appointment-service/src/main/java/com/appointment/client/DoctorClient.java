package com.appointment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.appointment.dto.DoctorDto;

@FeignClient(name = "DOCTOR-SERVICE")
public interface DoctorClient {
    
    @GetMapping("/doctor/getdoctor/{docterId}")
    DoctorDto getDoctor(@RequestHeader("Authorization") String token, @PathVariable Long docterId);
}
