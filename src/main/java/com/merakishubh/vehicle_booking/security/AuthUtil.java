package com.merakishubh.vehicle_booking.security;


import com.merakishubh.vehicle_booking.entity.Owner;
import com.merakishubh.vehicle_booking.entity.Renter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Owner owner){
        return Jwts.builder()
                .subject(owner.getEmail())
                .claim("ownerId", owner.getId().toString())
                .claim("role", "OWNER")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateAccessToken(Renter renter){
        return Jwts.builder()
                .subject(renter.getEmail())
                .claim("id", renter.getId())
                .claim("role", "RENTER")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSecretKey())
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getRoleFromToken(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public String getEmailFromToken(String token) {
        Claims claims =  Jwts.parser()
                                .verifyWith(getSecretKey())
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();

        return claims.getSubject();
    }


}
