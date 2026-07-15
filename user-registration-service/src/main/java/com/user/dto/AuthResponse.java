package com.user.dto;

import lombok.Data;

@Data
public class AuthResponse {
    
    private String jwt;
    private boolean status;
}
