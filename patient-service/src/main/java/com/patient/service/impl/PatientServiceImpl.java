package com.patient.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

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

    @Override
    public Patient createPatientProfile(Patient patient) throws PatientException {
        final String METHOD = "createPatientProfile";
        log.info("Inside methid {} "+METHOD);

        if(patient==null){
            log.error("patient is null");
            throw new PatientException("patient is null");
        }

        return patientRepository.save(patient);

    }

    @Override
    public Patient getSinglePatient(long patientId) throws PatientException {

        final String METHOD = "getSinglePatient";
        log.info("Inside {} "+METHOD);

        Optional<Patient> optionalPatient = patientRepository.findById(patientId);

        if(optionalPatient.isEmpty()){
            log.error("Inside {} "+METHOD+" could not found patient");
            throw new PatientException("patient not found");
        }

        log.info("Inside {} "+METHOD+" patient found!");
        return optionalPatient.get();

    }

    @Override
    public Patient updatePatient(long patientId, Patient updtProfile) throws PatientException {

        final String METHOD = "updatePatient";

        log.info("Inside {} "+METHOD);
       
        Patient existingPatient = getSinglePatient(patientId);

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
       
        Patient patient = getSinglePatient(patientId);

        patientRepository.delete(patient);
    }
    
}
