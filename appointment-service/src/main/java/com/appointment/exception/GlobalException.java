package com.appointment.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.appointment.model.ExceptionResponse;

@ControllerAdvice
public class GlobalException {
    
    @ExceptionHandler(AppointmentException.class)
    public ResponseEntity<ExceptionResponse> handleException(AppointmentException ae, WebRequest webRequest){

        return new ResponseEntity<>(new ExceptionResponse(ae.getMessage(), webRequest.getDescription(false), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }
}
