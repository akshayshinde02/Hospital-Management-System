package com.doctor.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.doctor.exception.DoctorException;
import com.doctor.model.Doctor;

public interface PhysicianService {
    
    public Doctor registerDoctor(Doctor doctor, String token) throws DoctorException;

    public Doctor viewSingleDoctor( String token, Long doctorId) throws DoctorException;

    public List<Doctor> getAllDoctors() throws DoctorException;

    public Doctor updateDoctorProfile( String token, Doctor doctor, Long doctorId) throws DoctorException;

    public void removeDoctor( String token, Long doctorId) throws DoctorException;

    public List<Doctor> getDoctors(Pageable pageable, String search);
}
