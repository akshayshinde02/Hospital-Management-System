package com.user.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        com.user.model.User user = userRepository.findByUsername(username)
        .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        return User.withUsername(user.getUsername())
        .password(user.getPassword())
        .roles(user.getRole().name())
        .build();
    }
    
}
