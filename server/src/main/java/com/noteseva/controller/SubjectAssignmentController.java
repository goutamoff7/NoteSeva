package com.noteseva.controller;

import com.noteseva.DTO.SubjectAssignmentDTO;
import com.noteseva.model.*;
import com.noteseva.service.AppCacheService;
import com.noteseva.service.DTOService;
import com.noteseva.service.SubjectAssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("subject")
@Tag(name = "SubjectAssignment APIs", description = "add, View and Search Subject")
public class SubjectAssignmentController {

    @Autowired
    SubjectAssignmentService subjectAssignmentService;

    @Autowired
    DTOService dtoService;

    @Autowired
    AppCacheService appCacheService;

    @Operation(summary = "Get all Subject Assignment")
    @GetMapping("/all")
    public ResponseEntity<?> getAllSubjectAssignment() {
        try {
            List<SubjectAssignmentDTO> SubjectAssignmentDTOList = subjectAssignmentService.getAllSubjectAssignment()
                    .stream()
                    .map(dtoService::convertToSubjectAssignmentDTO) // Convert SubjectAssignment -> SubjectAssignmentDTO
                    .toList();
            if (SubjectAssignmentDTOList.isEmpty())
                return new ResponseEntity<>("May be Subject Assignments are not available", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(SubjectAssignmentDTOList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Add Subject Assignment")
    @PostMapping("/add")
    public ResponseEntity<?> addSubject(@RequestBody @Valid SubjectAssignmentDTO subjectAssignmentDTO) {
        try {
            SubjectAssignment Assignment = subjectAssignmentService.findSubjectAssignment(subjectAssignmentDTO);
            if (Assignment != null)
                return new ResponseEntity<>("Subject assignment already exist", HttpStatus.BAD_REQUEST);
            SubjectAssignment subjectAssignment =
                    subjectAssignmentService.createAssignment(subjectAssignmentDTO);
            if (subjectAssignment != null) {
                appCacheService.reloadSubjectStructure();
                return new ResponseEntity<>(dtoService.convertToSubjectAssignmentDTO(subjectAssignment), HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Unable to add Subject", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
