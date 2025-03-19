package com.noteseva.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private String secretKey;

    public JwtService() {
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256);
            SecretKey sk = keyGenerator.generateKey();
            secretKey= Base64.getUrlEncoder().encodeToString(sk.getEncoded());
        }catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }

    }

    public String extractUsername(String token) {
        // extract the username from jwt token
        return extractClaims(token, Claims::getSubject);
    }

    public String generateToken(String username) {
        return Jwts
                .builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1 * 60 * 60 * 1000)) // 1 hr
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Base64.getUrlDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
