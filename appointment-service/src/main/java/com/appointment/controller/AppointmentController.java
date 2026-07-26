package com.appointment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.exception.AppointmentException;
import com.appointment.model.Appointment;
import com.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/appointment")
@Slf4j
@RequiredArgsConstructor
public class AppointmentController {
    
    private final AppointmentService appointmentService;

    @PostMapping("/create-appointment/{doctorId}")
    public ResponseEntity<Appointment> createAppointment(@RequestHeader("Authorization") String token, @RequestBody Appointment appointment, @PathVariable Long doctorId) throws AppointmentException{

        String METHOD = "createAppointment";
        log.info("inside controller {}"+METHOD);

        Appointment savedAppointment = appointmentService.createAppointment(token, appointment,doctorId);

        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> viewUserAppointments(@RequestHeader("Authorization") String token) throws AppointmentException{

        String METHOD = "viewUserAppointments";
        log.info("inside controller {}"+METHOD);

        List<Appointment> appointments = appointmentService.viewUserAppointments(token);

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Appointment> viewSingleAppointment(@RequestHeader("Authorization") String token, @PathVariable Long appointmentId) throws AppointmentException{

        String METHOD = "viewSingleAppointment";
        log.info("inside controller {}"+METHOD);

        Appointment appointment = appointmentService.viewSingleAppointment(token, appointmentId);

        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    
    @DeleteMapping("/cancel-appointment/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@RequestHeader("Authorization") String token, @PathVariable Long appointmentId) throws AppointmentException{

        String METHOD = "cancelAppointment";
        log.info("inside controller {}"+METHOD);

        appointmentService.cancelAppointment(token, appointmentId);

        return ResponseEntity.ok("Appointment get cancelled successfully");
    }

    @PutMapping("/update-appointment")
    public ResponseEntity<Appointment> changeAppointmentDateOrTime(@RequestHeader("Authorization") String token, @RequestBody Appointment appointment) throws AppointmentException{

        String METHOD = "changeAppointmentDateOrTime";
        log.info("inside controller {}"+METHOD);

        Appointment appointment2 = appointmentService.changeAppointmentDateOrTime(token, appointment);

        return new ResponseEntity<>(appointment2, HttpStatus.OK);
    }

}
