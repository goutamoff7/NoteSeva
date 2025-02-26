package com.noteseva.controller;

import com.noteseva.DTO.SubjectDTO;
import com.noteseva.model.*;
import com.noteseva.service.DTOService;
import com.noteseva.service.SubjectAssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/subject")
@Tag(name="Subject APIs",description = "View, Search, add Subject")
public class SubjectController
{

    @Autowired
    SubjectAssignmentService subjectAssignmentService;

    @Autowired
    DTOService dtoService;

    @Operation(summary = "")
    @GetMapping("/all")
    public ResponseEntity<?> getAllSubject()
    {
        try{
            List<SubjectAssignment> subjectList = subjectAssignmentService.getAllSubjects();
            if(subjectList!=null)
                return new ResponseEntity<>(subjectList,HttpStatus.OK);
            else
                return new ResponseEntity<>("List is not found!!",HttpStatus.NOT_FOUND);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something is wrong!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "")
    @PostMapping("/add")
    public ResponseEntity<?> addSubject(SubjectDTO subjectDTO)
    {
        try{
            Course course = dtoService.getCourse(subjectDTO.getCourse());
            Department department = dtoService.getDepartment(subjectDTO.getDepartment());
            Subject subject = dtoService.getSubject(subjectDTO.getSubject());
            SubjectAssignment subjectAssignment =
                    subjectAssignmentService.createAssignment(course,department,subject);
            if(subjectAssignment!=null)
                return new ResponseEntity<>(subjectAssignment,HttpStatus.CREATED);
            else
                return new ResponseEntity<>("List is not found!!",HttpStatus.NOT_FOUND);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something is wrong!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
