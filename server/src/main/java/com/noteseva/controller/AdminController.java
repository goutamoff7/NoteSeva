package com.noteseva.controller;

import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import com.noteseva.service.AdminService;
import com.noteseva.service.UtilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserRepository  userRepository;

    @Autowired
    UtilityService utilityService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid Users user) {
        try {
            String username = utilityService.extractUsernameFromEmail(user);
            if (userRepository.findByUsername(username)==null)
                return new ResponseEntity<>(adminService.registerAdmin(user), HttpStatus.CREATED);
            return new ResponseEntity<>("Admin already exist",HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Admin Registration Unsuccessful",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser() {
        try {
            return new ResponseEntity<>(adminService.getAllUser(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("No user available", HttpStatus.NOT_FOUND);
        }
    }

}
