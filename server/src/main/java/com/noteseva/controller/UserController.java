package com.noteseva.controller;

import com.noteseva.components.PasswordChange;
import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import com.noteseva.service.EmailService;
import com.noteseva.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logged-user")
public class LoggedInUserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PublicService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChange request){
        try {
            if(userService.changePassword(request)) {
                emailService.successfullPasswordChangingMail(getEmailId());
                return new ResponseEntity<>("Password changed successfully!!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Please enter valid credintials!!",HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Some internal problem!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public String getEmailId() {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        Users user=userRepo.findByUsername(username);
        String email=user.getEmail();
        return email;
    }
}
