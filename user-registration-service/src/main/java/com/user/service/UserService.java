package com.user.service;

import java.util.List;

import com.user.dto.AuthResponse;
import com.user.dto.LoginRequest;
import com.user.dto.UserDto;
import com.user.exception.UserException;
import com.user.model.User;

public interface UserService {
    
    public AuthResponse createUser(User user) throws UserException;

    public AuthResponse loginUser(LoginRequest loginRequest) throws UserException;

    public UserDto getSingleUser(String token) throws UserException;

    public List<User> getUsers() throws UserException;

    public User updateUser(User user, long userId) throws UserException;

    public void deleteUser(long userId) throws UserException;
}
