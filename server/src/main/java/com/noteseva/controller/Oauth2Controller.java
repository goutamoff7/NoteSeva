package com.noteseva.controller;

import com.noteseva.model.TokenResponse;
import com.noteseva.model.Users;
import com.noteseva.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Tag(name = "Oauth2.0 Login API", description = "Google and GitHub login")
public class Oauth2Controller {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    EmailService emailService;

    @Autowired
    UtilityService utilityService;

    @Autowired
    RedisService redisService;

    @GetMapping("/oauth2")
    public ResponseEntity<?> oauthLogin(@AuthenticationPrincipal OAuth2User oAuth2User) {

        if (oAuth2User == null) {
            System.out.println(oAuth2User);
            return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }

        String email = oAuth2User.getAttribute("email");
        String username = utilityService.extractUsernameFromEmail(email);   // username = abc123 when email = abc12@gmail.com
        try {
            userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException u) {
            try{
                Users newUser = userService.registerOauth2(oAuth2User);
                if (newUser != null) {
                    redisService.markEmailAsVerified(newUser.getEmail());
                    emailService.sendSuccessEmail(newUser.getEmail(),
                            newUser.getName());
                }
                return new ResponseEntity<>("User Registration Unsuccessful",
                        HttpStatus.SERVICE_UNAVAILABLE) ;
            }catch(Exception e){
                log.error(e.toString());
                return new ResponseEntity<>("Something Went Wrong!!",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        try {
            String accessToken = jwtService
                    .generateAccessToken(username);
            String refreshToken = jwtService
                    .generateRefreshToken(username);

            Users user = userService.saveRefreshToken(username, refreshToken);
            if(user!=null)
                return new ResponseEntity<>("Login Failed",
                        HttpStatus.SERVICE_UNAVAILABLE) ;

            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccessToken(accessToken);
            tokenResponse.setRefreshToken(refreshToken);

//         Set JWT in HttpOnly cookie
            ResponseCookie accessTokenCookie =
                    utilityService.getAccessTokenCookie(tokenResponse.getAccessToken());
            ResponseCookie refreshTokenCookie =
                    utilityService.getRefreshTokenCookie(tokenResponse.getRefreshToken());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
            headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

            Map<String, Object> response = new HashMap<>();
            response.put("name", oAuth2User.getAttribute("name"));
            response.put("email", oAuth2User.getAttribute("email"));
            response.put("picture", oAuth2User.getAttribute("picture"));
            response.put("tokenResponse", tokenResponse);
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("Login Failed - Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
