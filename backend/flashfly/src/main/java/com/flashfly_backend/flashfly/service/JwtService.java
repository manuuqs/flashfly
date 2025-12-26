package com.flashfly_backend.flashfly.service;

import com.flashfly_backend.flashfly.dtos.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {


    @Value("${jwt.secret}")
    private String secretKey;


    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("name", user.getName())
                .claim("authProvider", user.getAuthProvider())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


}
