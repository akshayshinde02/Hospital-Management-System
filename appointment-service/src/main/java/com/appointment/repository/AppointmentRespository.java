package com.appointment.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.appointment.model.Appointment;

import feign.Param;

@Repository
public interface AppointmentRespository extends JpaRepository<Appointment, Long>{
    

    List<Appointment> findByUserId(Long userId);

    // if doctor has appointment
    @Query("SELECT COUNT(a)>0 FROM Appointment a WHERE a.doctorId =:doctorId AND a.appointmentDate =:appointmentDate AND a.appointmentTime =:appointmentTime")
    boolean existDoctorAppointment (
        @Param("doctorId") Long doctorId,
        @Param("appointmentDate")LocalDate appointmentDate,
        @Param("appointmentTime")LocalTime appointmentTime
    );

    // if patient has appointment
    @Query("SELECT COUNT(a)>0 FROM Appointment a WHERE a.patientId =:patientId AND a.appointmentDate =:appointmentDate AND a.appointmentTime =:appointmentTime")
    boolean existPatientAppointment(
        @Param("patientId")Long patientId,
        @Param("appointmentDate")LocalDate appointmentDate,
        @Param("appointmentTime")LocalTime appointmentTime
    );
}
