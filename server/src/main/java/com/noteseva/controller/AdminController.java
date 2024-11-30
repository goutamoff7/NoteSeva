package com.noteseva.controller;

import com.noteseva.model.Users;
import com.noteseva.service.AdminService;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid Users user , BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            Map<String , String> errors=new HashMap<>();
            bindingResult.getFieldErrors().forEach(error->
                    errors.put(error.getField(),error.getDefaultMessage()));
            return  new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(adminService.registerAdmin(user), HttpStatus.CREATED);
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
