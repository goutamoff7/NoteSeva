package com.noteseva.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UtilityService {
    public void validateFile(MultipartFile file) {
        if (file.getContentType()==null || !file.getContentType().equals("application/pdf"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type. Only PDF are allowed.");
        else if (file.getSize() > 5 * 1024 * 1024)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File size must not exceed 5MB.");
    }

    public String extractUsernameFromEmail(String email) {
        return email.split("@")[0].toLowerCase();
    }

    public String generateFileHash(byte[] fileData) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] fileDataHash = messageDigest.digest(fileData);
        // Convert to a Base64 encoded string (easier to store in DB)
        return Base64.getEncoder().encodeToString(fileDataHash);
    }


}
