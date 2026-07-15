package com.patient.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.patient.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = PatientException.class)
    public ResponseEntity<ErrorResponse> handlePatientException(PatientException ex, WebRequest webRequest){

        ErrorResponse response = new ErrorResponse(ex.getMessage(), webRequest.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
       
    }
}
