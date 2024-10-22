package com.noteseva.controller;

import com.noteseva.model.Notes;
import com.noteseva.model.Subject;
import com.noteseva.service.NotesService;
import com.noteseva.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
public class SubjectController
{

    @Autowired
    SubjectService subjectService;

    //localhost:8080/subject/search?keyword=DBMS
    @GetMapping("/subject/search")
    public ResponseEntity<?> searchSubject(@RequestParam String keyword)
    {
        try {
            return new ResponseEntity<>(subjectService.searchSubject(keyword), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("No Result Found",HttpStatus.NOT_FOUND);
        }
    }

    //localhost:8080/subjects?department=CSE
    @GetMapping("/subjects")
    public ResponseEntity<?> getSubjects(@RequestParam String department)
    {
        try {
            List<Subject> subjectList = subjectService.getSubjects(department);
            if (subjectList != null)
                return new ResponseEntity<>(subjectList, HttpStatus.OK);
            else
                return new ResponseEntity<>("No Subject Found", HttpStatus.NOT_FOUND);
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?>setSubjects(@RequestBody List<Subject> subjects)
    {
        try{
            return new ResponseEntity<> (subjectService.setSubjects(subjects),HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?>setSubject(@RequestBody Subject subject)
    {
        try{
            return new ResponseEntity<>(subjectService.setSubject(subject),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
