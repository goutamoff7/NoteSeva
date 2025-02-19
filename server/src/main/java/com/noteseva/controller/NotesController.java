package com.noteseva.controller;

import com.noteseva.DTO.NotesDTO;
import com.noteseva.model.Notes;
import com.noteseva.service.DTOService;
import com.noteseva.service.NotesService;
import com.noteseva.service.UserDetailsServiceImpl;
import com.noteseva.service.UtilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("notes")
@Tag(name="Notes APIs",description = "View, Search, Upload and Download Notes")
public class NotesController {

    @Autowired
    NotesService notesService;

    @Autowired
    UtilityService utilityService;

    @Autowired
    DTOService dtoService;

    //localhost:8080/notes/all
    @Operation(summary = "")
    @GetMapping("/all")
    public ResponseEntity<?> getAllNotes() {
        try {
            return new ResponseEntity<>(notesService.getAllNotes(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("May be Notes are not available", HttpStatus.NOT_FOUND);
        }
    }

    //localhost:8080/notes/1
    @Operation(summary = "")
    @GetMapping("/{id}")
    public ResponseEntity<?> getNotes(@PathVariable Integer id) {
        try {
            Notes notes = notesService.getNotes(id);
            if (notes != null)
                return new ResponseEntity<>(notes, HttpStatus.OK);
            else
                return new ResponseEntity<>("May be this notes is not available!!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/notes/upload
    @Operation(summary = "")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadNotes(@RequestPart @Valid NotesDTO notesDTO,
                                         @RequestPart MultipartFile file) {
        try {
            // Validate the file
            utilityService.validateFile(file);

            Notes notes = dtoService.getNotes(notesDTO);

            //Getting uploader name
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            // Process and save notes and file
            Notes notes1 = notesService.uploadNotes(notes, file, username);
            return new ResponseEntity<>(notes1, HttpStatus.CREATED);

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/notes/download/1
    @Operation(summary = "")
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadNotes(@PathVariable Integer id) {
        try {
            Notes notes = notesService.getNotes(id);
            if (notes != null) {
                byte[] fileData = notes.getFileData();
                String fileType = notes.getFileType();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(fileType));
                return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
            } else
                return new ResponseEntity<>("May be this notes is not available!!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
