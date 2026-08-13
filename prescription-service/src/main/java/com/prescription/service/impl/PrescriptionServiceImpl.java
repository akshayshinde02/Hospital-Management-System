package com.prescription.service.impl;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prescription.client.AppointmentClient;
import com.prescription.client.AuthenticationClient;
import com.prescription.dto.AppointmentDto;
import com.prescription.dto.UserDto;
import com.prescription.exception.PrescriptionException;
import com.prescription.model.Prescription;
import com.prescription.model.PrescriptionMedicine;
import com.prescription.repository.PrescriptionRepository;
import com.prescription.service.PrescriptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository repository;
    private final AuthenticationClient authClient;
    private final PdfService pdfService;
    private final AppointmentClient appointmentClient;

    @Override
    public Prescription createPrescription(String token, Prescription prescription, Long appointmentId) throws PrescriptionException {

        String METHOD = "createPrescription";
        log.info("inside method {}", METHOD);

        if (token == null || prescription == null) {
            log.error("token or presription not null");
            throw new PrescriptionException("token or presecription should not null");
        }

        UserDto userDto = authClient.getUser(token);

        if (userDto == null || !userDto.getRole().equals("DOCTOR")) {
            throw new PrescriptionException("user not found");
        }

        AppointmentDto appointment = appointmentClient.getPatientDoctorAppointment(token, appointmentId);

        if(appointment.getAppointmentDate().isBefore(LocalDate.now()) || appointment.getAppointmentTime().isBefore(LocalTime.now())){

            prescription.setUserId(userDto.getUserId());
            prescription.setAppointmentId(appointmentId);
            prescription.setDoctorId(appointment.getDoctorId());
            prescription.setPatientId(appointment.getPatientId());
            // prescription.getMedicines().addAll(prescription.getMedicines());

        }else{
            throw new PrescriptionException("Appointment yet to complete!");
        }

        return repository.save(prescription);

    }

    @Override
    public Prescription getSinglePrescription(String token, Long prescriptionId) throws PrescriptionException {

        String METHOD = "getSinglePrescription";
        log.info("inside method {}", METHOD);

        if (token == null || prescriptionId == null) {
            log.error("token or prescriptionId not null");
            throw new PrescriptionException("token or prescriptionId should not null");
        }

        UserDto userDto = authClient.getUser(token);

        if (userDto == null) {
            throw new PrescriptionException("user not found");
        }

        Prescription prescription = repository.findById(prescriptionId)
                .orElseThrow(() -> new PrescriptionException("Prescription not found"));

        if (!prescription.getUserId().equals(userDto.getUserId())) {
            throw new PrescriptionException("Access denied");
        }

        return prescription;
    }

    @Override
    public List<Prescription> getAllPatientPrescriptions(String token,Long patientId) throws PrescriptionException {

        String METHOD = "getAllUserPrescriptions";
        log.info("inside method {}", METHOD);

        if (token == null) {
            log.error("token or userId not null");
            throw new PrescriptionException("token or userId should not null");
        }

        UserDto userDto = authClient.getUser(token);

        if (userDto == null) {
            throw new PrescriptionException("user not found");
        }

        return repository.findByUserId(patientId);
    }

    @Override
    public Prescription updatePrescription(String token, Prescription prescription) throws PrescriptionException {

        String METHOD = "updatePrescription";
        log.info("inside method {}", METHOD);

        if (token == null || prescription == null) {
            log.error("token or prescription not null");
            throw new PrescriptionException("token or prescription should not null");
        }

        UserDto userDto = authClient.getUser(token);

        if (userDto == null || !userDto.getRole().equals("DOCTOR")) {
            throw new PrescriptionException("user not found");
        }

        Prescription savedPrescription = repository.findById(prescription.getPrescriptionId()).orElseThrow(()-> new PrescriptionException("Prescription not found"));

        if(!savedPrescription.getDoctorId().equals(prescription.getDoctorId())){
            throw new PrescriptionException("Access denied");
        }

        List<PrescriptionMedicine> listOfMedician = prescription.getMedicines();
        savedPrescription.getMedicines().addAll(listOfMedician);

        return repository.save(savedPrescription);
    }

    @Override
    public void removePrescription(String token, Long prescriptionId) throws PrescriptionException {

        String METHOD = "removePrescription";
        log.info("inside method {}", METHOD);

        if (token == null || prescriptionId == null) {
            log.error("token or prescriptionId not null");
            throw new PrescriptionException("token or prescriptionId should not null");
        }

        UserDto userDto = authClient.getUser(token);

        if (userDto == null || !userDto.getRole().equals("DOCTOR")) {
            throw new PrescriptionException("user not found");
        }

        Prescription prescription = repository.findById(prescriptionId)
                .orElseThrow(() -> new PrescriptionException("Prescription not found"));

        if (!prescription.getUserId().equals(userDto.getUserId())) {
            throw new PrescriptionException("Access Denied");
        }

        repository.delete(prescription);

    }

    @Override
    public byte[] downloadPrescription(String token, Long prescriptionId) throws PrescriptionException {
      
        UserDto userDto = authClient.getUser(token);

        Prescription prescription = repository.findById(prescriptionId)
                .orElseThrow(()-> new PrescriptionException("Prescription not found"));

        if(!prescription.getUserId().equals(userDto.getUserId())){
            throw new PrescriptionException("Access Denied");
        }

        return pdfService.generatePrescriptionPdf(prescription);
    }

   

}
