package com.user.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {
    
    @Value("${jwt.secret.key}")
    private String SECRET;

    private SecretKey key;
    
    // call automatically when object is being created
    // not use then key is not being set
    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(Authentication auth){

        final String METHOD = "generateToken";

        log.info("Inside "+METHOD+" Generating jwt token...");

        String jwt = Jwts.builder()
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(new Date().getTime()+86400000))
                        .claim("username", auth.getName())
                        .signWith(key)
                        .compact();
        log.info("Inside "+METHOD+" Token Generated Successfully!");
        return jwt;

    }

    public String getUsernameFromJwtToken(String jwt){

        final String METHOD = "getUsernameFromJwtToken";

        log.info("Inside "+METHOD+" start method");

        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jwt)
            .getBody();

        String username = claims.get("username").toString();

        log.info("Inside "+METHOD+" Username retrived successfully!");

        return username;
    }

    public boolean isTokenValid(String token, UserDetails user){

        final String METHOD = "isTokenValid";
        log.info("Inside "+METHOD);

        String username = getUsernameFromJwtToken(token);

        boolean expValue = Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token).getBody().getExpiration()
            .before(new Date());
        
        return username.equals(user.getUsername()) && !expValue;

    }

}
