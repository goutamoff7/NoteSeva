package com.noteseva.service;

import com.noteseva.DTO.PYQDTO;
import com.noteseva.Pagination.PageResponse;
import com.noteseva.model.PYQ;
import com.noteseva.repository.PYQRepository;
import com.noteseva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    DTOService dtoService;

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

    public PageResponse<PYQDTO> getAllPYQ(
            String courseName, String departmentName, String subjectName,
            int pageNumber, int pageSize, String sortBy, String sortingOrder) {
        Sort sort = sortingOrder.equalsIgnoreCase("ASC") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<PYQ> pagePYQ = pyqRepository.getAllPYQ(
                courseName, departmentName, subjectName, pageable);
        if (!pagePYQ.getContent().isEmpty()) {
            List<PYQDTO> organizerDTOList = pagePYQ
                    .getContent().stream()
                    .map(pyq -> dtoService.convertToPYQDTO(pyq))
                    .toList();
            return PageResponse.getPageResponseDTO(pagePYQ, organizerDTOList);
        }
        return null;

    }

    public boolean isFileDataExist(String fileDataHash) {
        return pyqRepository.existsByFileDataHash(fileDataHash);
    }
}
