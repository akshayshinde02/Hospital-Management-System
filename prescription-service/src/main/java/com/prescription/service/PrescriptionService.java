package com.prescription.service;

import java.util.List;

import com.prescription.exception.PrescriptionException;
import com.prescription.model.Prescription;

public interface PrescriptionService {
    
    public Prescription createPrescription(String token, Prescription prescription, Long appointmentId) throws PrescriptionException;

    public Prescription getSinglePrescription(String token, Long prescriptionId) throws PrescriptionException;

    public List<Prescription> getAllPatientPrescriptions(String token, Long patientId) throws PrescriptionException;

    public Prescription updatePrescription(String token, Prescription prescription) throws PrescriptionException;

    public void removePrescription(String token, Long prescriptionId) throws PrescriptionException;

    public byte[] downloadPrescription(String token, Long prescriptionId) throws PrescriptionException;

}
