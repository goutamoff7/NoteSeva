package com.noteseva.service;

import com.noteseva.components.ForgotPassword;
import com.noteseva.components.PasswordChange;
import com.noteseva.model.Notes;
import com.noteseva.model.Role;
import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PublicService {

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

    @Autowired
    EmailService emailService;

    @Autowired
    OTPService otpService;

    public Users register(Users user) {
        String email = user.getEmail();
        if (!otpService.verifiedEmails.contains(email))
            throw new RuntimeException("Email verification required.");
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

    public boolean changePassword(PasswordChange request) {
        String newPassword=passwordEncoder.encode(request.getNewPassword());
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        Users user=userRepository.findByUsername(username);
        if(!passwordEncoder.matches(request.getOlderPassword(), user.getPassword()))
            return false;
        if(!request.getNewPassword().equals(request.getConfirmPassword()))
            return false;
        try {
            user.setPassword(newPassword);
            user.setTokenIssueTime(LocalDateTime.now());
            userRepository.save(user);
            return true;
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean resetPassword(ForgotPassword request) {
        if(!request.getPassword().equals(request.getConfirmPassword()))
            return false;
        if(otpService.verifiedEmails.contains(request.getEmail())){
            Users user=userRepository.findUsersByEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setTokenIssueTime(LocalDateTime.now());
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
