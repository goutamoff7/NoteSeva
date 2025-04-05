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
    UtilityService utilityService;


    public Users register(Users user) {
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

    public boolean verify(Users user) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword()
                        )
                );
        return authentication.isAuthenticated();

    }

    public Users saveRefreshToken(String username, String refreshToken){
        Users user = findByUsername(username);
        user.setRefreshToken(passwordEncoder.encode(refreshToken));
        return userRepository.save(user);
    }

    public Users deleteRefreshToken(String username){
        Users user = findByUsername(username);
        user.setRefreshToken(null);
        return userRepository.save(user);
    }

    public Users resetPassword(Users user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    public Users findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean validateRefreshToken(String refreshToken,String usernameClaimed){
        Users user = findByUsername(usernameClaimed);
        String savedRefreshToken = user.getRefreshToken();
        return isTokenMatch(refreshToken,savedRefreshToken);
    }

    public boolean isTokenMatch(String refreshToken, String savedRefreshToken){
        return passwordEncoder.matches(refreshToken,savedRefreshToken);
    }


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
