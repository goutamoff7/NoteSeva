package com.noteseva.service;

import com.noteseva.DTO.SubjectAssignmentDTO;
import com.noteseva.model.Department;
import com.noteseva.model.Subject;
import com.noteseva.model.SubjectAssignment;
import com.noteseva.repository.SubjectAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectAssignmentService
{
    @Autowired
    SubjectAssignmentRepository subjectAssignmentRepository;

    @Autowired
    DTOService dtoService;


    public List<SubjectAssignment> getAllSubjects() {
        return subjectAssignmentRepository.findAll();
    }

    public SubjectAssignment createAssignment(SubjectAssignmentDTO subjectAssignmentDTO) {
        Department department = dtoService.getDepartment(subjectAssignmentDTO);
        Subject subject = dtoService.getSubject(subjectAssignmentDTO);
        SubjectAssignment subjectAssignment = getSubjectAssignment(department,subject);
        return subjectAssignmentRepository.save(subjectAssignment);
    }

    public SubjectAssignment getSubjectAssignment(Department department, Subject subject) {
        SubjectAssignment subjectAssignment =
                subjectAssignmentRepository.findSubjectAssignment(department.getDepartment(), subject.getSubject());
        if (subjectAssignment != null)
            return subjectAssignment;
        else {
            SubjectAssignment newSubjectAssignment = new SubjectAssignment();
            newSubjectAssignment.setDepartment(department);
            newSubjectAssignment.setSubject(subject);
            return subjectAssignmentRepository.save(newSubjectAssignment);
        }
    }
}
