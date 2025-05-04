package com.noteseva.service;

import com.noteseva.constants.TokenExpiration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Service
public class UtilityService {

    public void validateFile(MultipartFile file) {
        if (file.getContentType()==null || !file.getContentType().equals("application/pdf"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type. Only PDF are allowed.");
        else if (file.getSize() > 5 * 1024 * 1024)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File size must not exceed 5MB.");
    }

    public String extractUsernameFromEmail(String email) {
        return email.split("@")[0].toLowerCase();
    }

    public ResponseCookie getAccessTokenCookie(String token){
        return  ResponseCookie
                .from("access-token", token)
                .httpOnly(true)
                .secure(false)
                //(false) Allows the cookie to be sent over both HTTP and HTTPS.
                //Ensures cookies are sent only over HTTPS (recommended for production).
                .path("/")
                .maxAge(Duration.ofMillis(TokenExpiration.ACCESS_TOKEN_EXPIRATION))
                .build();
    }

    public ResponseCookie getRefreshTokenCookie(String token){
        return  ResponseCookie
                .from("refresh-token", token)
                .httpOnly(true)
                .secure(false)
                //(false) Allows the cookie to be sent over both HTTP and HTTPS.
                //Ensures cookies are sent only over HTTPS (recommended for production).
                .path("/")
                .maxAge(Duration.ofMillis(TokenExpiration.REFRESH_TOKEN_EXPIRATION))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
