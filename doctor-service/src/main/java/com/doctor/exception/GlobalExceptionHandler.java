package com.doctor.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.doctor.model.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = DoctorException.class)
    public ResponseEntity<ExceptionResponse> handleException(DoctorException de, WebRequest webRequest){

        return new ResponseEntity<>(new ExceptionResponse(de.getMessage(), webRequest.getDescription(false), LocalDateTime.now()), HttpStatus.NOT_FOUND);
        
    }
}
