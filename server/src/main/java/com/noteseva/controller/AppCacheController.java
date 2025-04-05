package com.noteseva.controller;

import com.noteseva.DTO.SubjectAssignmentDTO;
import com.noteseva.service.AppCacheService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("public")
@Tag(name = "AppCache API", description = "Load Subject Structure from Database")
public class AppCacheController {

    @Autowired
    AppCacheService appCacheService;

    @GetMapping("/reload-subject-structure")
    public ResponseEntity<?> reloadSubjectStructure() {
        try {
            appCacheService.reloadSubjectStructure();
            return new ResponseEntity<>("Subject Structure Reloaded", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-subject-structure")
    public ResponseEntity<?> getSubjectStructure() {
        try {
            List<SubjectAssignmentDTO> subjectStructureList = appCacheService.getSubjectStructure();
            if (subjectStructureList.isEmpty())
                return new ResponseEntity<>("May be Subject Assignments are not available",
                        HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(subjectStructureList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

