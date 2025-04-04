package com.noteseva.service;

import com.noteseva.DTO.SubjectAssignmentDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppCacheService {

    @Autowired
    SubjectAssignmentService subjectAssignmentService;

    @Autowired
    DTOService dtoService;

    private List<SubjectAssignmentDTO> subjectStructureList;

    @Transactional
    public void reloadSubjectStructure() {
        subjectStructureList = new ArrayList<>();
        subjectStructureList = subjectAssignmentService.getAllSubjectAssignment()
                .stream()
                .map(dtoService::convertToSubjectAssignmentDTO) // Convert SubjectAssignment -> SubjectAssignmentDTO
                .toList();
    }

    public List<SubjectAssignmentDTO> getSubjectStructure() {
        return subjectStructureList;
    }


}
