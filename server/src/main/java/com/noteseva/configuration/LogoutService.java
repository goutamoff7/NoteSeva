package com.noteseva.configuration;

import com.noteseva.service.JwtService;
import com.noteseva.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogoutService implements LogoutHandler {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            String username = null;
            String accessToken = jwtService.extractToken(request,"access-token");
            if (accessToken!=null) {
                username = jwtService.extractUsername(accessToken);
            }
            if (username != null)
                userService.deleteRefreshToken(username);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
