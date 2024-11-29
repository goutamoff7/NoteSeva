package com.noteseva.controller;

import com.noteseva.model.Users;
import com.noteseva.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/register")
    public Users register(@RequestBody Users user)
    {
        return adminService.registerAdmin(user);
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser()
    {
        try {
            return new ResponseEntity<>(adminService.getAllUser(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("No user available",HttpStatus.NOT_FOUND);
        }
    }

}
