package com.GraduationProject.GraduationProject.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;


@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;


    public String generateToken(String username, Integer userId) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userId",userId);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        System.out.println("Token has been created: " + token); // Loglama
        return token;
    }

    public String extractUsername(String token) {
        String username = getClaims(token).getSubject();
        System.out.println("Username extracted from token " + username); // Loglama
        return username;
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        final Integer userId = extractUserId(token);

        SecurityContext context = SecurityContextHolder.getContext();

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        boolean isExpired = getClaims(token).getExpiration().before(new Date());
        System.out.println("Token Süresi Doldu mu: " + isExpired); // Loglama
        return isExpired;
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Doğrulama için verifyWith kullanılıyor
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public Integer extractUserId(String token) {
        Integer userId = getClaims(token).get("userId", Integer.class);
        if (userId == null) {
            System.err.println("User ID data not found in Token");
            return null;
        }
        System.out.println("User ID extracted from token " + userId);
        return userId;
    }


}
