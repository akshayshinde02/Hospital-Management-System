package com.appointment.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.appointment.client.AuthenticationClient;
import com.appointment.client.DoctorClient;
import com.appointment.client.PatientClient;
import com.appointment.dto.DoctorDto;
import com.appointment.dto.PatientDto;
import com.appointment.dto.UserDto;
import com.appointment.exception.AppointmentException;
import com.appointment.model.Appointment;
import com.appointment.model.AppointmentStatus;
import com.appointment.repository.AppointmentRespository;
import com.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AuthenticationClient authClient;
    private final DoctorClient doctorClient;
    private final PatientClient patientClient;
    private final AppointmentRespository repository;

    @Override
    public Appointment createAppointment(String token, Appointment appointment, Long doctorId)
            throws AppointmentException {

        String METHOD = "createAppointment";
        log.info("inside method {}" + METHOD);

        if (token == null || appointment == null) {
            throw new AppointmentException("token | appointment should not be null");
        }

        UserDto userDto = authClient.getUser(token);
        PatientDto patientDto = patientClient.getPatient(token);
        // DoctorDto doctorDto = doctorClient.getDoctor(token, appointment.getDoctorId());

        if(appointment.getAppointmentDate().isBefore(LocalDate.now())){
            throw new AppointmentException("Appointment date cannot be in the past");
        }

        if(appointment.getAppointmentDate().isEqual(LocalDate.now()) && appointment.getAppointmentTime().isBefore(LocalTime.now())){
            throw new AppointmentException("Appointment time cannot be in the past");
        }

        if(repository.existDoctorAppointment(doctorId, appointment.getAppointmentDate(), appointment.getAppointmentTime())){
            throw new AppointmentException("Doctor already booked at this time");
        }

        if(repository.existPatientAppointment(patientDto.getPatientId(), appointment.getAppointmentDate(), appointment.getAppointmentTime())){
            throw new AppointmentException("Patient already booked at this time");
        }

        Appointment savedAppointment = new Appointment();
        savedAppointment.setAppointmentDate(appointment.getAppointmentDate());
        savedAppointment.setAppointmentTime(appointment.getAppointmentTime());
        savedAppointment.setUserId(userDto.getUserId());
        savedAppointment.setPatientId(patientDto.getPatientId());
        savedAppointment.setDoctorId(doctorId);

        savedAppointment.setStatus(AppointmentStatus.PENDING);

        return repository.save(savedAppointment);

    }

    @Override
    public List<Appointment> viewUserAppointments(String token) throws AppointmentException {

        String METHOD = "viewUserAppointments";
        log.info("inside method {}" + METHOD);

        if (token == null) {
            throw new AppointmentException("token | userId should not be null");
        }

        UserDto userDto = authClient.getUser(token);

        return repository.findByUserId(userDto.getUserId());

    }

    @Override
    public Appointment viewSingleAppointment(String token, Long appointmentId) throws AppointmentException {

        String METHOD = "viewSingleAppointment";
        log.info("inside method {}" + METHOD);

        if (token == null || appointmentId == null) {
            throw new AppointmentException("token | userId should not be null");
        }

        authClient.getUser(token);

        Appointment appointment = repository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentException("appointment not found with " + appointmentId));


        return appointment;

    }

    @Override
    public void cancelAppointment(String token, Long appointmentId) throws AppointmentException {

        String METHOD = "cancelAppointment";
        log.info("inside method {}" + METHOD);

        if (token == null || appointmentId == null) {
            throw new AppointmentException("token | userId should not be null");
        }

        UserDto userDto = authClient.getUser(token);

        Appointment appointment = repository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentException("appointment not found with " + appointmentId));

        if (!userDto.getUserId().equals(appointment.getUserId())) {
            throw new AppointmentException("User not valid");
        }



        repository.deleteById(appointmentId);
    }

    @Override
    public Appointment changeAppointmentDateOrTime(String token, Appointment appointment) throws AppointmentException {

        String METHOD = "changeAppointmentDateOrTime";
        log.info("inside method {}" + METHOD);

        if (token == null || appointment == null) {
            throw new AppointmentException("token | userId should not be null");
        }

        UserDto userDto = authClient.getUser(token);

        Appointment existingAppointment = repository.findById(appointment.getAppointmentId())
                .orElseThrow(() -> new AppointmentException("Appointment not found"));

        if (!existingAppointment.getUserId().equals(userDto.getUserId())) {
            throw new AppointmentException("Access Denied");
        }

        if (existingAppointment.getStatus() == AppointmentStatus.CANCELED) {
            throw new AppointmentException(
                    "Cancelled appointment cannot be modified");
        }
        
        if (existingAppointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new AppointmentException(
                    "Completed appointment cannot be modified");
        }

        existingAppointment.setAppointmentDate(
                appointment.getAppointmentDate());

        existingAppointment.setAppointmentTime(
                appointment.getAppointmentTime());

        return repository.save(existingAppointment);
    }

}
