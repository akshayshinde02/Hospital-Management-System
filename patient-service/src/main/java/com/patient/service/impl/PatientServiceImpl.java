package com.patient.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.patient.client.AuthenticationClient;
import com.patient.dto.UserDto;
import com.patient.exception.PatientException;
import com.patient.model.Patient;
import com.patient.repository.PatientRepository;
import com.patient.service.PatientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;
    private final AuthenticationClient client;

    @Override
    public Patient createPatientProfile(Patient patient, String token) throws PatientException {
        final String METHOD = "createPatientProfile";
        log.info("Inside methid {} "+METHOD);

        if(patient==null){
            log.error("patient is null");
            throw new PatientException("patient is null");
        }

        UserDto user = client.getUser(token);

        if(user == null){
            throw new PatientException("User not Found");
        }

        if(!user.getRole().equals("PATIENT")){
            throw new PatientException("Only patient can create patient profile");
        }

        patient.setUserId(user.getUserId());

        return patientRepository.save(patient);

    }

    @Override
    public Patient getSinglePatient(long patientId, String token) throws PatientException {

        final String METHOD = "getSinglePatient";
        log.info("Inside {} "+METHOD);

        UserDto user = client.getUser(token);

        if(!user.getRole().equals("PATIENT")){
            throw new PatientException("only patient can access profile");
        }

        Patient optionalPatient = patientRepository.findById(patientId).orElseThrow(()-> new PatientException("Patient not found"));

        if(!optionalPatient.getUserId().equals(user.getUserId())){
            throw new PatientException("Access Denied");
        }

        log.info("Inside {} "+METHOD+" patient found!");
        return optionalPatient;

    }

    @Override
    public Patient updatePatient(long patientId, Patient updtProfile) throws PatientException {

        final String METHOD = "updatePatient";

        log.info("Inside {} "+METHOD);
       
        Patient existingPatient = patientRepository.findById(patientId).orElseThrow(()-> new PatientException("Patient with id not found!"));

        existingPatient.setAge(updtProfile.getAge());
        existingPatient.setContactNumber(updtProfile.getContactNumber());
        existingPatient.setGender(updtProfile.getGender());
        existingPatient.setSymptoms(updtProfile.getSymptoms());
        existingPatient.setName(updtProfile.getName());

        log.info("Inside {} "+METHOD+" patient updated successfully!");

        return patientRepository.save(existingPatient);

    }

    @Override
    public void deletePatient(long patientId) throws PatientException {
       
        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new PatientException("Patient with id not found!"));

        patientRepository.delete(patient);
    }
    
}
