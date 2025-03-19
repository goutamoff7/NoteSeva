package com.noteseva.controller;

import com.noteseva.DTO.UsersDTO;
import com.noteseva.components.ForgotPassword;
import com.noteseva.components.OTP;
import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import com.noteseva.service.*;
import com.noteseva.validation.LoginValidation;
import com.noteseva.validation.RegisterValidation;
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
    PublicService publicService;

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
    PublicService userService;

    //localhost:8080/public/register
    @Operation(summary = "Register User")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated(RegisterValidation.class) @RequestBody UsersDTO usersDTO) {
        try {
            Users user = dtoService.getUser(usersDTO);
            String username = utilityService.extractUsernameFromEmail(user.getEmail());
            if (userRepository.findByUsername(username)==null) {
                Users registeredUser = publicService.register(user);
                if(registeredUser!=null) {
                    emailService.sendEmail(registeredUser.getEmail());
                    return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
                }
                else
                    return new ResponseEntity<>("User Registration Unsuccessful",HttpStatus.SERVICE_UNAVAILABLE);
            }
            return new ResponseEntity<>("User already exist",HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
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

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody String email){
        try {
            if(userRepository.existsByEmail(email)){
                emailService.alertEmail(email);
                return new ResponseEntity<>("The otp is sent successfully!!",HttpStatus.OK);
            }
            return new ResponseEntity<>("This email is not registered to do this action.",HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Some internal issues!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/forgotPassword-otp-verification")
    public ResponseEntity<?> forgotPasswordOtpVerification(@RequestBody OTP request){
        try {
            if(otpService.verifyOTP(request))
                return new ResponseEntity<>("OTP verification done successfully!!",HttpStatus.OK);
            return new ResponseEntity<>("Please enter the valid otp/email",HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Some internal issues!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/forgotPassword-set-password")
    public ResponseEntity<?> setPassword(@RequestBody ForgotPassword request){
        try {
            if(userService.resetPassword(request)) {
                emailService.successfullPasswordChangingMail(request.getEmail());
                return new ResponseEntity<>("Password is changed successfully!!",HttpStatus.OK);
            }
            return new ResponseEntity<>("Please enter valid details!!",HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Some internal issues!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
