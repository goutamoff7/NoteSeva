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

<<<<<<< HEAD
    @Autowired
    EmailService emailService;

    @Autowired
    OTPService otpService;

=======
>>>>>>> 932b2fb83a54a1b9f8a55132a21be9bfc8a4e346
    public Users register(Users user) {
        String email = user.getEmail();
        if (!otpService.verifiedEmails.contains(email))
            throw new RuntimeException("Email verification required.");
        user.setUsername(utilityService.extractUsernameFromEmail(user.getEmail())); // username = substring of email id, before @ symbol
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
<<<<<<< HEAD
        emailService.sendEmail(user.getEmail());
        Users users=userRepository.save(user);
        if(users!=null) {
            emailService.sendEmail(email);
            return users;
        }
        throw  new RuntimeException("User Registration failed!!");
=======
        return userRepository.save(user);
>>>>>>> 932b2fb83a54a1b9f8a55132a21be9bfc8a4e346
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
