package com.noteseva.controller;

import com.noteseva.DTO.*;
import com.noteseva.model.Users;
import com.noteseva.service.DTOService;
import com.noteseva.service.EmailService;
import com.noteseva.service.UserService;
import com.noteseva.service.UtilityService;
import com.noteseva.validation.ChangePasswordValidation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    DTOService dtoService;


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
            String username = userService.getCurrentUsername();
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

    @GetMapping("/get-user-details")
    public ResponseEntity<?> getUserDetails(){
        try{
            String username =userService.getCurrentUsername();
            Users user = userService.findByUsername(username);
            if(user==null)
                return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
            UserDetailsDTO userDetailsDTO = dtoService.convertToUserDetailsDTO(user);
            return new ResponseEntity<>(userDetailsDTO,HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!"
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-user-details")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO updateUserDTO){
        try{
           Users user =userService.updateUser(updateUserDTO);
            if(user!=null)
                return new ResponseEntity<>("User Updated successfully",
                        HttpStatus.OK);
            return new ResponseEntity<>("User Update Failed",
                    HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-uploaded-docs")
    public ResponseEntity<?> getUploadedDocs(){
        try{
            String username =userService.getCurrentUsername();
            Users user = userService.findByUsername(username);
            if(user==null)
                return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
            UploadedDocsDTO uploadedDocsDTO = dtoService.convertToUploadedDocsDTO(user);
            if(uploadedDocsDTO == null)
                return new ResponseEntity<>("Failed to Get Uploaded Docs",
                        HttpStatus.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(uploadedDocsDTO,HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!"
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-bookmarked-docs")
    public ResponseEntity<?> getBookmarkedDocs(){
        try{
            String username =userService.getCurrentUsername();
            Users user = userService.findByUsername(username);
            if(user==null)
                return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
            BookmarkedDocsDTO bookmarkedDocsDTO = dtoService.convertToBookmarkedDocsDTO(user);
            if(bookmarkedDocsDTO == null)
                return new ResponseEntity<>("Failed to Get Uploaded Docs",
                        HttpStatus.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(bookmarkedDocsDTO,HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!"
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthStatus(Authentication authentication) {
            return new ResponseEntity<>("User/Admin is authenticated", HttpStatus.OK);
    }


}

