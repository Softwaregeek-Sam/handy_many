package com.sumit.handymany.auth.service;

import com.sumit.handymany.user.model.Role;
import com.sumit.handymany.user.model.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey key;

    @PostConstruct
    public void initKey(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles().stream()
                .map(Role::getName)
                .toList());
        claims.put("userId", user.getId());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getPhone())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration ))
                .signWith(key)
                .compact();

    }

    public boolean isTokenValid(String token){
         try{
              Jwts.parser()
                      .verifyWith( key)
                      .build()
                      .parseSignedClaims(token);
                      return true;
         }catch (SecurityException | ExpiredJwtException e){
              return false;
         }
    }

    public String extractPhone(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    // === Extract roles ===
    public List<String> extractRoles(String token) {
        Map<String, Object> claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Object rolesObject = claims.get("roles");
        if (rolesObject instanceof List<?> list) {
            // Convert elements to string
            return list.stream().map(Object::toString).toList();
        }
        return List.of();
    }

    public Long extractUserId(String token) {
        Map<String, Object> claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();


        Object userIdObject = claims.get("userId");
        if (userIdObject instanceof Number number) {
            return number.longValue();
        }
        return null;
    }




}
