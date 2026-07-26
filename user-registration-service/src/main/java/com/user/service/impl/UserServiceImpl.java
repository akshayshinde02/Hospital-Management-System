package com.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.config.JwtService;
import com.user.dto.AuthResponse;
import com.user.dto.LoginRequest;
import com.user.dto.UserDto;
import com.user.exception.UserException;
import com.user.model.Role;
import com.user.model.User;
import com.user.repository.UserRepository;
import com.user.service.CustomUserDetailsService;
import com.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    @Override
    public AuthResponse createUser(User user) throws UserException {

        final String METHOD = "createUser";

        log.info("Inside "+METHOD+" Creating new user");

        if (user == null) {
            log.error("User Object is null");
            throw new UserException("User cannot be null");
        }

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            log.warn("User already exixt with id {}", user.getUserId());
            throw new UserException("User already present in the database");
        }

        if (user.getUsername() == null || user.getPassword() == null) {
            throw new UserException("Username or Password cannot be empty");
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        if(user.getRole().equals(Role.PATIENT)){
            newUser.setRole(Role.PATIENT);
        }else{
            newUser.setRole(Role.DOCTOR);
        }
        newUser.setCreated_at(new Date());

        User savedUser = userRepository.save(newUser);
        log.info("Inside "+METHOD+" User created successfully with id{}", savedUser.getUserId());

        UserDetails userDetails  = userDetailsService.loadUserByUsername(newUser.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateToken(authentication);

        log.info("Inside "+METHOD+" Creating authResponse->");
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setStatus(true);
        
        log.info("Inside "+METHOD+" AuthResponse Created Successfully");

        return authResponse;
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) throws UserException {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication = loginAuthenticate(username, password);

        String token = jwtService.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return authResponse;
    }

    private Authentication loginAuthenticate(String username, String password){

        final String METHOD = "loginAuthentication";
        log.info("Inside "+METHOD);
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if(user==null){
            log.error("Inside "+METHOD+" user not found");
            throw new UserException("Invalid Username or password");
        }
        if(!passwordEncoder.matches(password, user.getPassword())){
            log.error("Inside "+METHOD+" user password not matched");
            throw new UserException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public UserDto getSingleUser(String token) throws UserException {

        log.info("Fetaching user with token {}");

        if (token == null) {
            log.error("Invalid user token{}");
            throw new UserException("Token must be provided");
        }

        token = token.substring(7);
        String userName = jwtService.getUsernameFromJwtToken(token);

        if (userName == null) {
            log.error("userName not found with id {}", userName);
            throw new UserException("User not found with Username");
        }

        Optional<User> user = userRepository.findByUsername(userName);

        log.info("User fetch successfully");

        UserDto userDto = new UserDto();
        userDto.setUserId(user.get().getUserId());
        userDto.setUsername(user.get().getUsername());
        userDto.setRole(user.get().getRole());

        return userDto;
    }

    @Override
    public List<User> getUsers() throws UserException {

        log.info("Get all user {}");

        List<User> userList = userRepository.findAll();

        if (userList.isEmpty()) {
            log.error("users not found!");
            throw new UserException("User is Empty");
        }
        log.info("Users fetch successfully");

        return userList;
    }

    @Override
    public User updateUser(User user, long userId) throws UserException {

        log.info("updating user {}", user);
        if (user == null || userId <= 0) {
            log.error("user not present in the database");
            throw new UserException("user not present");
        }

        Optional<User> existing_user = userRepository.findById(userId);

        if (existing_user.isEmpty()) {
            log.error("user not found with id {}", userId);
            throw new UserException("User not found with id");
        }


        User existingUser = existing_user.get();
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setUpdated_at(new Date());

        log.info("user updated successfully");
        return userRepository.save(existingUser);

    }

    @Override
    public void deleteUser(long userId) throws UserException {
        log.info("Deleting the user", userId);
        userRepository.deleteById(userId);
        log.info("user{} deleted successfully", userId);
    }

  

}
