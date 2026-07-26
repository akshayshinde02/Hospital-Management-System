package com.appointment.service;

import java.util.List;

import com.appointment.exception.AppointmentException;
import com.appointment.model.Appointment;

public interface AppointmentService {
    
    public Appointment createAppointment(String token, Appointment appointment, Long doctorId) throws AppointmentException;

    public List<Appointment> viewUserAppointments(String token) throws AppointmentException;

    public Appointment viewSingleAppointment(String token, Long appointmentId) throws AppointmentException;

    public void cancelAppointment(String token, Long appointmentId) throws AppointmentException;

    public Appointment changeAppointmentDateOrTime(String token, Appointment appointment) throws AppointmentException;

}
