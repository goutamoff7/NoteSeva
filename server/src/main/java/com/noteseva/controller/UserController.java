package com.noteseva.controller;

import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import com.noteseva.service.UserService;
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
@RequestMapping("public")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilityService utilityService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid Users user) {
        try {
            String username = utilityService.extractUsernameFromEmail(user);
            if (userRepository.findByUsername(username)==null)
                return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
            return new ResponseEntity<>("User already exist",HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("User Registration Unsuccessful", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Users user) {
        try {
            return new ResponseEntity<>(userService.verify(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Login Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
