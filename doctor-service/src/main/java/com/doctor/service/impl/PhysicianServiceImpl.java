package com.doctor.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.doctor.client.AuthenticationClient;
import com.doctor.dto.UserDto;
import com.doctor.exception.DoctorException;
import com.doctor.model.Doctor;
import com.doctor.repository.PhysicianRepository;
import com.doctor.service.PhysicianService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhysicianServiceImpl implements PhysicianService{

    private final PhysicianRepository repository;

    private final AuthenticationClient client;

    @Override
    public Doctor registerDoctor(Doctor doctor, String token) throws DoctorException {
        
        log.info("inside method {}"+"registerDoctor");
        if(doctor==null){
            throw new DoctorException("Doctor cannot be empty");
        }

        UserDto userDto = client.getUser(token);

        if(userDto == null){
            throw new DoctorException("User not Found");
        }

        if(!userDto.getRole().equals("DOCTOR")){
            throw new DoctorException("Only doctor can create doctor profile");
        }

        Optional<Doctor> doctorExist = repository.findByUserId(userDto.getUserId());

        if(doctorExist.isPresent()){
            throw new DoctorException("Doctor already exist");
        }

        doctor.setUserId(userDto.getUserId());

        return repository.save(doctor);

    }

    @Override
    public Doctor viewSingleDoctor( String token, Long doctorId) throws DoctorException {
        log.info("inside method {}"+"viewSingleDoctor");

        UserDto userDto = client.getUser(token);

        if(userDto == null){
            throw new DoctorException("User not Found");
        }

        if(!userDto.getRole().equals("DOCTOR")){
            throw new DoctorException("Only doctor can create doctor profile");
        }

        Doctor doctor = repository.findById(doctorId)
        .orElseThrow(()->
            new DoctorException("Doctor with if not found"+doctorId)
        );

        if(doctor.getUserId()!=userDto.getUserId()){
            throw new DoctorException("Invalid doctor");
        }

       return doctor;
    }

    @Override
    public Doctor updateDoctorProfile( String token, Doctor doctor, Long doctorId) throws DoctorException {
        log.info("inside method {}"+"updateDoctorProfile");

        UserDto userDto = client.getUser(token);

        if(userDto == null){
            throw new DoctorException("User not Found");
        }

        if(!userDto.getRole().equals("DOCTOR")){
            throw new DoctorException("Only doctor can create doctor profile");
        }

        Doctor existingDoctor = repository.findById(doctorId).orElseThrow(()-> new DoctorException("Doctor not found with id"+doctorId));

        if(existingDoctor.getUserId()!=userDto.getUserId()){
            throw new DoctorException("Invalid doctor");
        }
        

        existingDoctor.setDoctorName(doctor.getDoctorName());
        existingDoctor.setExperties(doctor.getExperties());
        existingDoctor.setQualification(doctor.getQualification());
        existingDoctor.setDepartment(doctor.getDepartment());
        existingDoctor.setDoctorAge(doctor.getDoctorAge());

        return repository.save(existingDoctor);
    }
    @Override
    public void removeDoctor( String token, Long doctorId) throws DoctorException {
        log.info("inside method {}"+"removeDoctor");

        UserDto userDto = client.getUser(token);

        if(userDto == null){
            throw new DoctorException("User not Found");
        }

        if(!userDto.getRole().equals("DOCTOR")){
            throw new DoctorException("Only doctor can create doctor profile");
        }

        Doctor existingDoctor = repository.findById(doctorId).orElseThrow(()-> new DoctorException("Doctor not found!"));

        if(existingDoctor.getUserId()!=userDto.getUserId()){
            throw new DoctorException("Invalid doctor");
        }
        
        repository.deleteById(doctorId);
    }

    @Override
    public List<Doctor> getAllDoctors() throws DoctorException {
        log.info("inside method {}"+"getAllDoctors");
        List<Doctor> listOfDoctors = repository.findAll();;

        if(listOfDoctors.isEmpty()){
            throw new DoctorException("No doctor found!");
        }
        return listOfDoctors;
    }

    @Override
    public List<Doctor> getDoctors(Pageable pageable, String search) {
       
        log.info("inside getDoctor()");

        if(search == null){
            return repository.findAll(pageable).getContent();
        } else{

            return repository.findByDoctorName(search,pageable).getContent();
        }
    }
    
}
