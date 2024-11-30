package com.noteseva.controller;

import com.noteseva.model.Users;
import com.noteseva.service.UserService;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid Users user , BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            Map<String , String> errors=new HashMap<>();
            bindingResult.getFieldErrors().forEach(error->
                    errors.put(error.getField(),error.getDefaultMessage()));
            return  new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user)
    {
        return new ResponseEntity<>(userService.verify(user),HttpStatus.OK);
    }

}
