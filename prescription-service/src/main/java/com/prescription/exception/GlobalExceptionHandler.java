package com.prescription.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.prescription.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(PrescriptionException.class)
    public ResponseEntity<ErrorResponse> handlerException(PrescriptionException pe, WebRequest req){

        return new ResponseEntity<>(
            new ErrorResponse(LocalDateTime.now(), 
                req.getDescription(false), pe.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MicroserviceCommunicationException.class)
    public ResponseEntity<ErrorResponse> handlerServiceException(MicroserviceCommunicationException pe, WebRequest req){

        return new ResponseEntity<>(
            new ErrorResponse(LocalDateTime.now(), 
                req.getDescription(false), pe.getMessage()),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

}
