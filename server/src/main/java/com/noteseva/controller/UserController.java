package com.noteseva.controller;

import com.noteseva.DTO.PasswordDTO;
import com.noteseva.model.Users;
import com.noteseva.service.EmailService;
import com.noteseva.service.UserService;
import com.noteseva.service.UtilityService;
import com.noteseva.validation.ChangePasswordValidation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("user")
@Tag(name = "User APIs", description = "Manage and Modify User")
public class UserController {

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
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            String username = SecurityContextHolder
                    .getContext().getAuthentication().getName();
            Users savedUser = userService.deleteRefreshToken(username);
            if (savedUser != null) {
                ResponseCookie accessTokenCookie = utilityService.getAccessTokenCookie();
                ResponseCookie refreshTokenCookie = utilityService.getRefreshTokenCookie();

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
                headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

                return new ResponseEntity<>("Logout Successfully", headers, HttpStatus.OK);
            }
            return new ResponseEntity<>("Logout Failed", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

