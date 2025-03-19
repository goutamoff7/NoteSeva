package com.noteseva.controller;

import com.noteseva.model.Users;
import com.noteseva.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Oauth2.0 Login API", description = "Google and GitHub login")
public class Oauth2Controller {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PublicService publicService;

    @Autowired
    JwtService jwtService;

    @Autowired
    EmailService emailService;

    @Autowired
    UtilityService utilityService;

    @GetMapping("/oauth2")
    public ResponseEntity<?> oauthLogin(@AuthenticationPrincipal OAuth2User user) {

        if (user == null) {
            System.out.println(user);
            return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }

        String email = user.getAttribute("email");
        String username = utilityService.extractUsernameFromEmail(email);   // username = abc123 when email = abc12@gmail.com
        try {
            userDetailsService.loadUserByUsername(username);
        } catch (Exception e) {
            Users newUser = publicService.registerOauth2(user);
            if (newUser != null)
                emailService.sendEmail(email);
        }
        String jwtToken = jwtService.generateToken(username);
        Map<String, Object> response = new HashMap<>();
        response.put("name", user.getAttribute("name"));
        response.put("email", user.getAttribute("email"));
        response.put("picture", user.getAttribute("picture"));
        response.put("jwt_token", jwtToken);
        return new ResponseEntity<>(response, HttpStatus.OK);


    }
}
