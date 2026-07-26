package com.doctor.controller;

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

import com.doctor.exception.DoctorException;
import com.doctor.model.Doctor;
import com.doctor.service.PhysicianService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/doctor")
public class PhysicianController {

    private final PhysicianService service;

    @PostMapping("/create-profile")
    public ResponseEntity<Doctor> createDoctor(@RequestHeader("Authorization") String token, @RequestBody Doctor doctor) throws DoctorException{

        String METHOD = "createDoctor()";
        log.info("inside controller {}"+METHOD);
        Doctor savedDoctor = service.registerDoctor(doctor, token);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @GetMapping("/getdoctor/{doctorId}")
    public ResponseEntity<Doctor> getDoctor(@RequestHeader("Authorization") String token, @PathVariable Long doctorId) throws DoctorException{

        String METHOD = "getDoctor";
        log.info("inside controller {}"+METHOD);
        Doctor doctor = service.viewSingleDoctor(token, doctorId);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @PutMapping("/update-profile/{doctorId}")
    public ResponseEntity<Doctor> updateProfile(@RequestHeader("Authorization") String token, @RequestBody Doctor doctor, @PathVariable Long doctorId) throws DoctorException{

        String METHOD = "updateProfile";
        log.info("inside controller {}"+METHOD);
        Doctor updatedDoctor = service.updateDoctorProfile(token, doctor, doctorId);
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

    @DeleteMapping("/delete-profile/{docterId}")
    public ResponseEntity<String> deleteProfile(@RequestHeader("Authorization") String token, @PathVariable Long docterId) throws DoctorException{

        String METHOD = "deleteProfile";
        log.info("inside controller {}"+METHOD);
        service.removeDoctor(token, docterId);
        return ResponseEntity.ok("Doctor profile deleted");
    }

    @GetMapping("/all-profile")
    public ResponseEntity<List<Doctor>> getAllProfile() throws DoctorException{

        String METHOD = "getAllProfile";
        log.info("inside controller {}"+METHOD);
        List<Doctor> listOfDoctors = service.getAllDoctors();
        return new ResponseEntity<>(listOfDoctors, HttpStatus.OK);
    }
    
}
