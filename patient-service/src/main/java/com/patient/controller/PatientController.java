package com.patient.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patient.exception.PatientException;
import com.patient.model.Patient;
import com.patient.service.PatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;



@Slf4j
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {
    
    private final PatientService patientService;

    @PostMapping("/create-patient")
    public ResponseEntity<Patient> addPatient(@RequestHeader("Authorization") String token, @RequestBody @Valid Patient patient) throws PatientException{
        
        final String METHOD = "addPatient";
        log.info("Insdie controller{} "+METHOD);
        
        Patient savedPatient = patientService.createPatientProfile(patient,token);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @GetMapping("/view/{patientId}")
    public ResponseEntity<Patient> getPatient(@RequestHeader("Authorization") String token, @PathVariable Long patientId) throws PatientException{

        final String METHOD = "getPatient";
        log.info("Inside controller "+METHOD);

        Patient patient = patientService.getSinglePatient(patientId,token);

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @GetMapping("/get/patient")
    public ResponseEntity<Patient> getPatientByUserId(@RequestHeader("Authorization") String token) throws PatientException{
        final String METHOD = "getPatientByUserId";
        log.info("Inside controller "+METHOD);

        Patient patient = patientService.viewPatientByUserId(token);

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }
    

    @DeleteMapping("/delete-patient/{patientId}")
    public ResponseEntity<String> deletePatient(@RequestHeader("Authorization") String token, @PathVariable Long patientId) throws PatientException{
        final String METHOD = "deletePatient";
        log.info("Inside controller ",METHOD);

        patientService.deletePatient(token, patientId);

        return ResponseEntity.ok("Patient Deleted");
    }
    
    @PutMapping("/update-patient/{patientId}")
    public ResponseEntity<Patient> updatePatient(@RequestHeader("Authorization") String token, @PathVariable Long patientId, @RequestBody Patient patient) throws PatientException{
        final String METHOD = "updatePatient";
        log.info("Inside controller "+METHOD);

        Patient updatedPatient = patientService.updatePatient(token, patientId,patient);
        
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }
    
}
