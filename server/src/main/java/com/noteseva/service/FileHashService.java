package com.noteseva.service;

import com.noteseva.model.FileHash;
import com.noteseva.repository.FileHashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class FileHashService {

    @Autowired
    FileHashRepository fileHashRepository;

    public String generateFileDataHash(byte[] fileData) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] fileDataHash = messageDigest.digest(fileData);
        // Convert to a Base64 encoded string (easier to store in DB)
        return Base64.getEncoder().encodeToString(fileDataHash);
    }

    public FileHash save(String fileDataHash) {
        FileHash fileHash = new FileHash();
        fileHash.setFileDataHash(fileDataHash);
        return fileHashRepository.save(fileHash);
    }

    public boolean isFileDataHashExist(String fileDataHash) {
        return fileHashRepository.existsByFileDataHash(fileDataHash);
    }


}
