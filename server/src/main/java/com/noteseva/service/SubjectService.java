package com.noteseva.service;

import com.noteseva.model.PYQ;
import com.noteseva.model.Subject;
import com.noteseva.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SubjectService
{
    @Autowired
    SubjectRepository subjectRepository;

    public List<Subject> searchSubject(String keyword)
    {
        return subjectRepository.searchSubject(keyword);
    }

    /*public List<Subject> getSubjects(String department)
    {
        return subjectRepository.getSubjects(department);
    }*/

    public List<Subject> setSubjects(List<Subject> subject)
    {
        return subjectRepository.saveAll(subject);
    }
    public Subject setSubject(Subject subject)
    {
        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects()
    {
        return subjectRepository.findAll();
    }
}
