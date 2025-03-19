package com.noteseva.controller;

import com.noteseva.components.OTP;
import com.noteseva.service.EmailService;
import com.noteseva.service.OTPService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
@Tag(name = "Email Validation APIs", description = "Validate Email through OTP")
public class EmailValidationController {

    @Autowired
    OTPService otpService;

    @Autowired
    EmailService emailService;

    //localhost:8080/public/generate-otp
    @Operation(summary = "Generate OTP for Email validation")
    @PostMapping("/generate-otp")
    public ResponseEntity<?> validateEmail(@RequestBody String email){
        try {
            emailService.sendOTP(email);
            return new ResponseEntity<>("OTP sent successfully!!", HttpStatus.CREATED);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Some issues arises!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //localhost:8080/public/verify-otp
    @Operation(summary = "Verify OTP for Email validation")
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody OTP request){
        try {
            if(otpService.verifyOTP(request))
                return new ResponseEntity<>("OTP is Valid , Email Validation successful!",HttpStatus.OK);
            return new ResponseEntity<>("Invalid OTP!!",HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Some issues arises!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
