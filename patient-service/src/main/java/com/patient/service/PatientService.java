package com.patient.service;

import com.patient.exception.PatientException;
import com.patient.model.Patient;

public interface PatientService {
    
    public Patient createPatientProfile(Patient patient) throws PatientException;

    public Patient getSinglePatient(long patientId) throws PatientException;

    public Patient updatePatient(long patientId, Patient updtProfile) throws PatientException;

    public void deletePatient(long patientId) throws PatientException;
}
