package com.noteseva.service;

import com.noteseva.model.Users;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UtilityService {
    public void validateFile(MultipartFile file) {
        if (file.getContentType()==null || !file.getContentType().equals("application/pdf"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type. Only PDF are allowed.");
        else if (file.getSize() > 5 * 1024 * 1024)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File size must not exceed 5MB.");
    }

    public String extractUsernameFromEmail(Users user) {
        String email = user.getEmail().toLowerCase();
        int endIndex = email.indexOf('@');
        return email.substring(0, endIndex);
    }


}