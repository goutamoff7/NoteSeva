package com.noteseva.service;

import com.noteseva.model.Notes;
import com.noteseva.repository.NotesRepository;
import com.noteseva.repository.SubjectDepartmentRepository;
import com.noteseva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class NotesService {
    @Autowired
    NotesRepository notesRepository;

    @Autowired
    UserRepository userRepository;


    public Notes uploadNotes(Notes notes, MultipartFile file, String username) throws IOException {
        notes.setFileName(file.getOriginalFilename());
        notes.setFileType(file.getContentType());
        notes.setFileData(file.getBytes());
        notes.setUser(userRepository.findByUsername(username));
        notes.setDate(LocalDate.now());
        return notesRepository.save(notes);
    }

    public List<Notes> getAllNotes() {
        return notesRepository.findAll();
    }

    public Notes getNotes(Integer id) {
        return notesRepository.findById(id).orElse(null);
    }
}
