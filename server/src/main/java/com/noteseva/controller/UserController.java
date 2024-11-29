package com.noteseva.controller;

import com.noteseva.model.Users;
import com.noteseva.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("public")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Users register(@RequestBody Users user)
    {
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user)
    {
        return userService.verify(user);
    }

}
