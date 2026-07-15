package com.user.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.user.model.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(UserException ex, WebRequest request){

        ExceptionResponse response = new ExceptionResponse(
            ex.getMessage(),
            request.getDescription(false),
            LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
