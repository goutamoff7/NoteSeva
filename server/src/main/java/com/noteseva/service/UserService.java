package com.noteseva.service;

import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    UtilityService utilityService;


    public Users changePassword(String username, String oldPassword, String newPassword) throws IllegalArgumentException{
        Users user = userRepository.findByUsername(username);
        if(passwordMatch(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("Provided Wrong Password");
    }

    public boolean passwordMatch(String givenPassword, String savedPassword){
        return passwordEncoder.matches(givenPassword,savedPassword);
    }
}
