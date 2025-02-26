package com.noteseva.service;

import com.noteseva.model.SubjectAssignment;
import com.noteseva.repository.SubjectAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService
{
    @Autowired
    SubjectAssignmentRepository subjectAssignmentRepository;


    public List<SubjectAssignment> getAllSubjects() {
        return subjectAssignmentRepository.findAll();
    }
}
