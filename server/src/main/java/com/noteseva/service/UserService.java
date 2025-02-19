package com.noteseva.service;

import com.noteseva.model.Role;
import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    UtilityService utilityService;

    public Users register(Users user) {
        user.setUsername(utilityService.extractUsernameFromEmail(user.getEmail())); // username = substring of email id, before @ symbol
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }

    public Users registerOauth2(OAuth2User oAuth2User) {
        Users user = new Users();
        user.setName(oAuth2User.getAttribute("given_name"));
        user.setEmail(oAuth2User.getAttribute("email"));
        user.setUsername(utilityService.extractUsernameFromEmail(user.getEmail()));
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }

    public String verify(Users user) {
        String username=utilityService.extractUsernameFromEmail(user.getEmail());
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                username,
                                user.getPassword()
                        )
                );
        if (authentication.isAuthenticated())
            return jwtService.generateToken(username);
        return "Login Failed";
    }

}
