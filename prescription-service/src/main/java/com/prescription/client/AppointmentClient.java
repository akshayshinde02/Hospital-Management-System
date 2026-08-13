package com.prescription.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.prescription.dto.AppointmentDto;

@FeignClient(name = "APPOINTMENT-SERVICE")
public interface AppointmentClient {
    
    @GetMapping("/appointment/appointment/{appointmentId}")
    AppointmentDto getPatientDoctorAppointment(@RequestHeader("Authorization") String token, @PathVariable Long appointmentId);
}
