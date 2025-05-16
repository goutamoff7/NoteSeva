package com.noteseva.service;

import com.noteseva.DTO.NotesDTO;
import com.noteseva.Pagination.PageResponse;
import com.noteseva.model.Notes;
import com.noteseva.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotesService {
    @Autowired
    NotesRepository notesRepository;

    @Autowired
    UserService userService;

    @Autowired
    DTOService dtoService;


    public Notes uploadNotes(Notes notes, MultipartFile file, String username) throws IOException {
        notes.setDocumentType("Notes");
        notes.setFileName(file.getOriginalFilename());
        notes.setFileType(file.getContentType());
        notes.setFileData(file.getBytes());
        notes.setUser(userService.findByUsername(username));
        notes.setUploadDateTime(LocalDateTime.now());
        return notesRepository.save(notes);
    }

    public Notes getNotes(Integer id) {
        return notesRepository.findById(id).orElse(null);
    }

    public PageResponse<NotesDTO> getAllNotes(
            String courseName, String departmentName, String subjectName,
            int pageNumber, int pageSize, String sortBy, String sortingOrder) {
        Sort sort = sortingOrder.equalsIgnoreCase("ASC") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Notes> pageNotes = notesRepository.getAllNotes(
                courseName, departmentName, subjectName, pageable);
        if (!pageNotes.getContent().isEmpty()) {
            List<NotesDTO> notesDTOList = pageNotes
                    .getContent().stream()
                    .map(notes -> dtoService.convertToNotesDTO(notes))
                    .toList();
            return PageResponse.getPageResponseDTO(pageNotes, notesDTOList);
        }
        return null;
    }
}
