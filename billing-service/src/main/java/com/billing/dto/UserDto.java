package com.billing.dto;

import lombok.Data;

@Data
public class UserDto {
    
    private Long userId;
    private String username;
    private String role;
}
