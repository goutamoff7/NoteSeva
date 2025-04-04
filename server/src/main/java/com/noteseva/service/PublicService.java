package com.noteseva.service;

import com.noteseva.model.Role;
import com.noteseva.model.TokenExpiration;
import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class PublicService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    UtilityService utilityService;

    @Autowired
    TokenExpiration tokenExpiration;


    public Users register(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }

    public Users registerOauth2(OAuth2User oAuth2User) {
        Users user = new Users();
        user.setName(oAuth2User.getAttribute("given_name"));
        user.setEmail(oAuth2User.getAttribute("email"));
        user.setUsername(utilityService.extractUsernameFromEmail(user.getEmail()));
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }

    public boolean verify(Users user) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword()
                        )
                );
        return authentication.isAuthenticated();

    }

    public Users saveRefreshToken(String username, String refreshToken){
        Users user = findByUsername(username);
        user.setRefreshToken(passwordEncoder.encode(refreshToken));
        return userRepository.save(user);
    }

    public Users resetPassword(Users user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    public ResponseCookie getAccessTokenCookie(String token){
       return  ResponseCookie
                .from("access-token", token)
                .httpOnly(true)
                .secure(false)
                //(false) Allows the cookie to be sent over both HTTP and HTTPS.
                //Ensures cookies are sent only over HTTPS (recommended for production).
                .path("/")
                .maxAge(Duration.ofSeconds(tokenExpiration.getAccessTokenExpiration()))
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
                .maxAge(Duration.ofSeconds(tokenExpiration.getRefreshTokenExpiration()))
                .build();
    }

    public Users findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean validateRefreshToken(String refreshToken,String usernameClaimed){
        Users user = findByUsername(usernameClaimed);
        String savedRefreshToken = user.getRefreshToken();
        return isTokenMatch(refreshToken,savedRefreshToken);
    }

    public boolean isTokenMatch(String refreshToken, String savedRefreshToken){
        return passwordEncoder.matches(refreshToken,savedRefreshToken);
    }
}

