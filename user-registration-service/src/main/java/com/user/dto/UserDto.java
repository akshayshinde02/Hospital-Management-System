package com.user.dto;

import com.user.model.Role;

import lombok.Data;

@Data
public class UserDto {
    
    private Long userId;
    private String username;
    private Role role;
}
