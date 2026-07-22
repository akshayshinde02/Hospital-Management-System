package com.patient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.patient.dto.UserDto;

@FeignClient(name = "USER-REGISTRATION-SERVICE")
// @FeignClient(name = "http://localhost:8080")
public interface AuthenticationClient {
    
    @GetMapping("/auth/user/token")
    UserDto getUser(@RequestHeader("Authorization") String token);
}
