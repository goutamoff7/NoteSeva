package com.noteseva.controller;

import com.noteseva.DTO.UsersDTO;
import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import com.noteseva.service.DTOService;
import com.noteseva.service.UserService;
import com.noteseva.service.UtilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("public")
@Tag(name = "User APIs", description = "Register and Login User")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilityService utilityService;

    @Autowired
    DTOService dtoService;

    //localhost:8080/public/register
    @Operation(summary = "")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsersDTO userDTO) {
        try {
            Users user = dtoService.getUser(userDTO);
            String username = utilityService.extractUsernameFromEmail(user.getEmail());
            if (userRepository.findByUsername(username)==null)
                return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
            return new ResponseEntity<>("User already exist",HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("User Registration Unsuccessful", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //localhost:8080/public/login
    @Operation(summary = "")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UsersDTO userDTO) {
        try {
            Users user = dtoService.getUser(userDTO);
            return new ResponseEntity<>(userService.verify(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Login Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
