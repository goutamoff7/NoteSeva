package com.noteseva.service;

import com.noteseva.DTO.OrganizerDTO;
import com.noteseva.Pagination.PageResponse;
import com.noteseva.model.Organizer;
import com.noteseva.repository.OrganizerRepository;
import com.noteseva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrganizerService {
    @Autowired
    OrganizerRepository organizerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DTOService dtoService;

    public Organizer uploadOrganizer(Organizer organizer, MultipartFile file, String username) {
        try {
            organizer.setFileName(file.getOriginalFilename());
            organizer.setFileType(file.getContentType());
            organizer.setFileData(file.getBytes());
            organizer.setUser(userRepository.findByUsername(username));
            organizer.setDate(LocalDate.now());
        } catch (Exception e) {
            System.out.println(e.getMessage() + "from service class.");
        }
        return organizerRepository.save(organizer);
    }

    public Organizer getOrganizer(Integer id) {
        return organizerRepository.findById(id).orElse(null);
    }

    public PageResponse<OrganizerDTO> getAllOrganizer(
            String courseName, String departmentName, String subjectName,
            int pageNumber, int pageSize, String sortBy, String sortingOrder) {
        Sort sort = sortingOrder.equalsIgnoreCase("ASC") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Organizer> pageOrganizer = organizerRepository.getAllOrganizer(
                courseName, departmentName, subjectName, pageable);
        if (!pageOrganizer.getContent().isEmpty()) {
            List<OrganizerDTO> organizerDTOList = pageOrganizer
                    .getContent().stream()
                    .map(organizer -> dtoService.convertToOrganizerDTO(organizer))
                    .toList();
            return PageResponse.getPageResponseDTO(pageOrganizer, organizerDTOList);
        }
        return null;
    }

    public boolean isFileDataExist(String fileDataHash) {
        return organizerRepository.existsByFileDataHash(fileDataHash);
    }
}
