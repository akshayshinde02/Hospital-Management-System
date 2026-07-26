package com.doctor.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.doctor.dto.UserDto;

@FeignClient(name = "USER-REGISTRATION-SERVICE")
public interface AuthenticationClient {
    
    @GetMapping("/auth/user/token")
    UserDto getUser(@RequestHeader("Authorization") String token);
}
