package com.noteseva.controller;

import com.noteseva.DTO.QueryDTO;
import com.noteseva.DTO.PasswordDTO;
import com.noteseva.DTO.UsersDTO;
import com.noteseva.model.Query;
import com.noteseva.model.TokenResponse;
import com.noteseva.model.Users;
import com.noteseva.service.*;
import com.noteseva.validation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("public")
@Tag(name = "Public APIs", description = "Register and Login User")
public class PublicController {

    @Autowired
    UtilityService utilityService;

    @Autowired
    DTOService dtoService;

    @Autowired
    EmailService emailService;

    @Autowired
    OTPService otpService;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    RedisService redisService;

    @Autowired
    JwtService jwtService;

    @Autowired
    QueryService queryService;

    //localhost:8080/public/generate-otp
    @Operation(summary = "Generate OTP for Email validation")
    @PostMapping("/generate-otp")
    public ResponseEntity<?> generateOTP(@Validated(GenerateOTPValidation.class)
                                         @RequestBody PasswordDTO passwordDTO) {
        try {
            String email = passwordDTO.getEmail();
            String otp = otpService.generateOTP(email);
            emailService.sendOTP(email, otp);
            return new ResponseEntity<>("OTP sent successfully!!", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/public/verify-otp
    @Operation(summary = "Validate OTP for Email validation")
    @PostMapping("/verify-otp")
    public ResponseEntity<?> validateOTP(@Validated(VerifyOTPValidation.class)
                                         @RequestBody PasswordDTO passwordDTO) {
        try {
            if (otpService.validateOTP(passwordDTO.getEmail(), passwordDTO.getOtp()))
                return new ResponseEntity<>("OTP is Valid , Email Validation successful!", HttpStatus.OK);
            return new ResponseEntity<>("Invalid OTP!!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/public/register
    @Operation(summary = "Register User")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated(RegisterValidation.class) @RequestBody UsersDTO usersDTO) {
        try {
            String password = usersDTO.getPassword();
            String confirmPassword = usersDTO.getConfirmPassword();
            if (!password.equals(confirmPassword))
                return new ResponseEntity<>("Password and Confirm Password should match", HttpStatus.BAD_REQUEST);
            Users user = dtoService.getUser(usersDTO);
            String email = user.getEmail();
            String username = utilityService.extractUsernameFromEmail(email);
            if (userService.findByUsername(username) != null)
                return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);
            else if (!redisService.getAndDeleteEmailVerified(email))
                return new ResponseEntity<>("Email Verification Required", HttpStatus.BAD_REQUEST);
            Users registeredUser = userService.register(user);
            if (registeredUser != null) {
                emailService.sendSuccessEmail(registeredUser.getEmail(),
                        registeredUser.getName());
                return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
            } else
                return new ResponseEntity<>("User Registration Unsuccessful", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/public/login
    @Operation(summary = "Login User")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated(LoginValidation.class) @RequestBody UsersDTO usersDTO) {
        try {
            Users user = dtoService.getUser(usersDTO);
            if (!authenticationService.verify(user))
                return new ResponseEntity<>("Invalid Credentials!", HttpStatus.UNAUTHORIZED);
            String username = user.getUsername();
            String accessToken = jwtService.generateAccessToken(username);
            String refreshToken = jwtService.generateRefreshToken(username);

            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccessToken(accessToken);
            tokenResponse.setRefreshToken(refreshToken);

            user = userService.setRefreshTokenAndLastLoginTime(username, refreshToken);
            if(user==null)
                return new ResponseEntity<>("Login Failed",
                        HttpStatus.SERVICE_UNAVAILABLE) ;
            // Set JWT in HttpOnly cookie
            ResponseCookie accessTokenCookie =
                    utilityService.getAccessTokenCookie(tokenResponse.getAccessToken());
            ResponseCookie refreshTokenCookie =
                    utilityService.getRefreshTokenCookie(tokenResponse.getRefreshToken());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
            headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

            return new ResponseEntity<>(tokenResponse, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Login Failed - Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Forgot Password")
    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Validated(ForgotPasswordValidation.class)
                                            @RequestBody PasswordDTO passwordDTO) {
        try {
            String email = passwordDTO.getEmail();
            String username = utilityService.extractUsernameFromEmail(email);
            String password = passwordDTO.getNewPassword();
            String confirmPassword = passwordDTO.getConfirmPassword();
            if (!password.equals(confirmPassword))
                return new ResponseEntity<>("New Password and Confirm Password should match", HttpStatus.BAD_REQUEST);
            Users user = userService.findByUsername(username);
            if (user == null)
                return new ResponseEntity<>("User not exist", HttpStatus.BAD_REQUEST);
            else if (!redisService.getAndDeleteEmailVerified(email))
                return new ResponseEntity<>("Email Verification Required", HttpStatus.BAD_REQUEST);
            Users savedUser = userService.resetPassword(user, passwordDTO.getNewPassword());
            if (savedUser != null) {
                emailService.successfulPasswordChangingMail(email, savedUser.getName());
                return new ResponseEntity<>("Password is changed successfully!!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Password change Unsuccessful",
                    HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> getAccessToken(HttpServletRequest request) {
        try {
            String refreshToken = jwtService.extractToken(request, "refresh-token");
            String username = jwtService.extractUsername(refreshToken);
            if (userService.validateRefreshToken(refreshToken,username)) {
                String newAccessToken = jwtService
                        .generateAccessToken(username);
                TokenResponse tokenResponse = new TokenResponse();
                tokenResponse.setAccessToken(newAccessToken);
                tokenResponse.setRefreshToken(refreshToken);

                // Set JWT in HttpOnly cookie
                ResponseCookie accessTokenCookie =
                        utilityService.getAccessTokenCookie(tokenResponse.getAccessToken());
                ResponseCookie refreshTokenCookie =
                        utilityService.getRefreshTokenCookie(tokenResponse.getRefreshToken());

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
                headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

                return new ResponseEntity<>(tokenResponse, headers, HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid or missing refresh token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthStatus(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            return new ResponseEntity<>("User is authenticated", HttpStatus.OK);
        }
        return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
    }

    //localhost:8080/public/contact-us
    @Operation(summary = "Contact Us")
    @PostMapping("/contact-us")
    public ResponseEntity<?> raiseQuery(@Valid @RequestBody QueryDTO queryDTO) {
        try {
            Query query = dtoService.getQuery(queryDTO);
            Query savedQuery = queryService.save(query);
            if (savedQuery != null) {
                emailService.sendQueryEmail(savedQuery.getEmail(),
                        savedQuery.getFirstName(),savedQuery.getQuery());
                return new ResponseEntity<>(savedQuery, HttpStatus.CREATED);
            } else
                return new ResponseEntity<>("Failed to Raise Query", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

