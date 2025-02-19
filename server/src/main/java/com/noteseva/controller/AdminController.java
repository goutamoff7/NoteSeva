package com.noteseva.controller;

import com.noteseva.DTO.UsersDTO;
import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import com.noteseva.service.AdminService;
import com.noteseva.service.DTOService;
import com.noteseva.service.UtilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@Tag(name = "Admin APIs", description = "Create admin, Get all user info")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilityService utilityService;

    @Autowired
    DTOService dtoService;

    //localhost:8080/admin/register
    @Operation(summary = "")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsersDTO userDTO) {
        try {
            Users user = dtoService.getUser(userDTO);
            String username = utilityService.extractUsernameFromEmail(user.getEmail());
            if (userRepository.findByUsername(username) == null)
                return new ResponseEntity<>(adminService.registerAdmin(user), HttpStatus.CREATED);
            return new ResponseEntity<>("Admin already exist", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Admin Registration Unsuccessful", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/admin/get-all-user
    @Operation(summary = "")
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
