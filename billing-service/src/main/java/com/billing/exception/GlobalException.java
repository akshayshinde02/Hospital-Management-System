package com.billing.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.billing.model.ErrorResponse;

@ControllerAdvice
public class GlobalException {
    
    @ExceptionHandler(value = BillingException.class)
    public ResponseEntity<ErrorResponse> handleException(BillingException be, WebRequest request){

        return new ResponseEntity<>(
            new ErrorResponse(LocalDateTime.now(), 
                request.getDescription(false), be.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
