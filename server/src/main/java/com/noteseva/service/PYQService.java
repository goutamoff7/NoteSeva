package com.noteseva.service;

import com.noteseva.model.PYQ;
import com.noteseva.repository.PYQRepository;
import com.noteseva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PYQService {
    @Autowired
    PYQRepository pyqRepository;

    @Autowired
    UserRepository userRepository;

    public PYQ uploadPYQ(PYQ pyq, MultipartFile file, String username) throws IOException {
        pyq.setFileName(file.getOriginalFilename());
        pyq.setFileType(file.getContentType());
        pyq.setFileData(file.getBytes());
        pyq.setUser(userRepository.findByUsername(username));
        pyq.setDate(LocalDate.now());
        return pyqRepository.save(pyq);
    }

    public PYQ getPYQ(Integer id) {
        return pyqRepository.findById(id).orElse(null);
    }

    public List<PYQ> getAllPYQ(String courseName,
                               String departmentName,
                               String subjectName) {
        return pyqRepository.getAllPYQ(courseName,departmentName,subjectName);
    }

    public boolean isFileDataExist(String fileDataHash) {
        return pyqRepository.existsByFileDataHash(fileDataHash);
    }
}
