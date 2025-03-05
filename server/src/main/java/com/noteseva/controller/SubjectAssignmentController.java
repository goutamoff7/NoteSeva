package com.noteseva.controller;

import com.noteseva.DTO.SubjectAssignmentDTO;
import com.noteseva.model.*;
import com.noteseva.service.DTOService;
import com.noteseva.service.SubjectAssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/subject")
@Tag(name="SubjectAssignment APIs",description = "add, View and Search Subject")
public class SubjectAssignmentController
{

    @Autowired
    SubjectAssignmentService subjectAssignmentService;

    @Autowired
    DTOService dtoService;

    @Operation(summary = "Get all Subject Assignment")
    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<?> getAllSubjectAssignment()
    {
        try{
            List<SubjectAssignmentDTO> SubjectAssignmentDTOList = subjectAssignmentService.getAllSubjectAssignment()
                    .stream()
                    .map(dtoService::convertToSubjectAssignmentDTO) // Convert Notes -> NotesDTO
                    .toList();
            if (SubjectAssignmentDTOList.isEmpty()) {
                return new ResponseEntity<>("May be Subject Assignments are not available",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(SubjectAssignmentDTOList, HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something is wrong!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Add Subject Assignment")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addSubject(@RequestBody @Valid SubjectAssignmentDTO subjectAssignmentDTO)
    {
        try{
            SubjectAssignment Assignment = subjectAssignmentService.findSubjectAssignment(subjectAssignmentDTO);
            if(Assignment!=null)
                return new ResponseEntity<>("Subject assignment already exist",HttpStatus.BAD_REQUEST);
            SubjectAssignment subjectAssignment =
                    subjectAssignmentService.createAssignment(subjectAssignmentDTO);
            if(subjectAssignment!=null)
                return new ResponseEntity<>(dtoService.convertToSubjectAssignmentDTO(subjectAssignment),HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Unable to add Subject",HttpStatus.SERVICE_UNAVAILABLE);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something is wrong!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
