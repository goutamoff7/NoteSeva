package com.noteseva.controller;

import com.noteseva.DTO.PasswordDTO;
import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import com.noteseva.service.EmailService;
import com.noteseva.service.UserService;
import com.noteseva.service.UtilityService;
import com.noteseva.validation.ChangePasswordValidation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs", description = "Manage and Modify User")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    UtilityService utilityService;

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Validated(ChangePasswordValidation.class)
                                            @RequestBody PasswordDTO passwordDTO) {
        try {
            String oldPassword = passwordDTO.getOldPassword();
            String newPassword = passwordDTO.getNewPassword();
            String confirmPassword = passwordDTO.getConfirmPassword();
            if (oldPassword.equals(newPassword))
                return new ResponseEntity<>("Old Password and New Password must be different", HttpStatus.BAD_REQUEST);
            else if (!newPassword.equals(confirmPassword))
                return new ResponseEntity<>("New Password and Confirm Password should match", HttpStatus.BAD_REQUEST);
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Users savedUser = userService.changePassword(
                    username,
                    passwordDTO.getOldPassword(),
                    passwordDTO.getNewPassword()
                    );
            if (savedUser != null) {
                emailService.successfulPasswordChangingMail(savedUser.getEmail(), savedUser.getName());
                return new ResponseEntity<>("Password is changed successfully!!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Password change Unsuccessful",
                    HttpStatus.SERVICE_UNAVAILABLE);
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)  // Change to true in production
                .path("/")
                .maxAge(0) // Expire immediately
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("Logged out successfully");
    }
}
