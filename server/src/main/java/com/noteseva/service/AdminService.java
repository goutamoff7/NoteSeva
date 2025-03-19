package com.noteseva.service;

import com.noteseva.model.Notes;
import com.noteseva.model.Role;
import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilityService utilityService;

    public Users registerAdmin(Users user) {
        user.setUsername(utilityService.extractUsernameFromEmail(user.getEmail()));  // username = substring of email id, before @ symbol
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_ADMIN);
        return userRepository.save(user);
    }

    public List<Users> getAllUser() {
        return userRepository.findAll();
    }
}
