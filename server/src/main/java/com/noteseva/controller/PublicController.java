package com.noteseva.controller;

import com.noteseva.DTO.PasswordDTO;
import com.noteseva.DTO.UsersDTO;
import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import com.noteseva.service.*;
import com.noteseva.validation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
@Tag(name = "Public APIs", description = "Register and Login User")
public class PublicController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilityService utilityService;

    @Autowired
    DTOService dtoService;

    @Autowired
    EmailService emailService;

    @Autowired
    OTPService otpService;

    @Autowired
    PublicService publicService;

    @Autowired
    RedisService redisService;

    //localhost:8080/public/generate-otp
    @Operation(summary = "Generate OTP for Email validation")
    @PostMapping("/generate-otp")
    public ResponseEntity<?> generateOTP(@Validated(GenerateOTPValidation.class)
                                             @RequestBody PasswordDTO passwordDTO){
        try {
            String email = passwordDTO.getEmail();
            String otp = otpService.generateOTP(email);
            emailService.sendOTP(email,otp);
            return new ResponseEntity<>("OTP sent successfully!!", HttpStatus.CREATED);
        }catch(Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>("Some issues arises!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //localhost:8080/public/verify-otp
    @Operation(summary = "Validate OTP for Email validation")
    @PostMapping("/verify-otp")
    public ResponseEntity<?> validateOTP(@Validated(VerifyOTPValidation.class)
                                           @RequestBody PasswordDTO passwordDTO){
        try {
            if(otpService.validateOTP(passwordDTO.getEmail(),passwordDTO.getOtp()))
                return new ResponseEntity<>("OTP is Valid , Email Validation successful!",HttpStatus.OK);
            return new ResponseEntity<>("Invalid OTP!!",HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Some issues arises!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/public/register
    @Operation(summary = "Register User")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated(RegisterValidation.class) @RequestBody UsersDTO usersDTO) {
        try {
            String password = usersDTO.getPassword();
            String confirmPassword = usersDTO.getConfirmPassword();
            if(!password.equals(confirmPassword)                 )
                return new ResponseEntity<>("Password and Confirm Password should match",HttpStatus.BAD_REQUEST);
            Users user = dtoService.getUser(usersDTO);
            String email = user.getEmail();
            String username = utilityService.extractUsernameFromEmail(email);
            if (userRepository.findByUsername(username) != null)
                return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);
            else if (!redisService.isEmailVerified(email))
                return new ResponseEntity<>("Email Verification Required", HttpStatus.BAD_REQUEST);
            Users registeredUser = publicService.register(user);
            if (registeredUser != null) {
                emailService.sendSuccessEmail(registeredUser.getEmail());
                return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
            } else
                return new ResponseEntity<>("User Registration Unsuccessful", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //localhost:8080/public/login
    @Operation(summary = "Login User")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated(LoginValidation.class) @RequestBody UsersDTO usersDTO) {
        try {
            Users user = dtoService.getUser(usersDTO);
            return new ResponseEntity<>(publicService.verify(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Login Failed", HttpStatus.INTERNAL_SERVER_ERROR);
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
            if(!password.equals(confirmPassword)                 )
                return new ResponseEntity<>("New Password and Confirm Password should match",HttpStatus.BAD_REQUEST);
            Users user = userRepository.findByUsername(username);
            if ( user== null)
                return new ResponseEntity<>("User not exist", HttpStatus.BAD_REQUEST);
            else if (!redisService.isEmailVerified(email))
                return new ResponseEntity<>("Email Verification Required", HttpStatus.BAD_REQUEST);
            Users savedUser = publicService.resetPassword(user,passwordDTO.getNewPassword());
            if (savedUser!=null) {
                emailService.successfulPasswordChangingMail(email,savedUser.getName());
                return new ResponseEntity<>("Password is changed successfully!!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Password change Unsuccessful",
                    HttpStatus.SERVICE_UNAVAILABLE) ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Some internal issues!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
