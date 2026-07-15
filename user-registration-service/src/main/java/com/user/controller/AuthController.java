package com.user.controller;

import org.springframework.web.bind.annotation.RestController;
import com.user.dto.AuthResponse;
import com.user.dto.LoginRequest;
import com.user.model.User;
import com.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody @Valid User user) {
       
        final String METHOD = "siginUp";
        log.info("inside Auth Controller method "+METHOD);

        AuthResponse authResponse = userService.createUser(user);
        return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {

        final String METHOD = "siginin";
        log.info("inside Auth Controller method "+METHOD);

        AuthResponse authResponse = userService.loginUser(loginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }
}
