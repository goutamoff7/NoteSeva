package com.noteseva.controller;

import com.noteseva.DTO.NotesDTO;
import com.noteseva.Pagination.PageResponse;
import com.noteseva.model.Notes;
import com.noteseva.service.DTOService;
import com.noteseva.service.NotesService;
import com.noteseva.service.UtilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
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

    //localhost:8080/notes/all?
    //courseName=BTECH &
    //departmentName=CSE &
    //subjectName=Basic Electrical Engineering
    //pageNumber=0&
    //pageSize=12&
    //sortBy=id&
    //sortingOrder=ASC
    @Operation(summary = "Fetch all notes")
    @GetMapping("/all")
    public ResponseEntity<?> getAllNotes(
            @RequestParam String courseName,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false,defaultValue = "0") int pageNumber,
            @RequestParam(required = false,defaultValue = "12") int pageSize,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "ASC") String sortingOrder) {
        try {
            PageResponse<NotesDTO> notesDTOPageResponse = notesService
                    .getAllNotes(courseName,departmentName,subjectName,
                            pageNumber,pageSize,sortBy,sortingOrder);
            if (notesDTOPageResponse==null)
                return new ResponseEntity<>("May be Notes are not available",
                        HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(notesDTOPageResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/notes/1
    @Operation(summary = "Fetch notes by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getNotes(@PathVariable Integer id) {
        try {
            Notes notes = notesService.getNotes(id);
            if (notes != null)
                return new ResponseEntity<>(dtoService.convertToNotesDTO(notes),
                        HttpStatus.OK);
            else
                return new ResponseEntity<>("May be this notes is not available!!",
                        HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/notes/upload
    @Operation(summary = "Upload notes")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadNotes(@RequestPart @Valid NotesDTO notesDTO,
                                         @RequestPart MultipartFile file) {
        try {
            // Validate the file
            utilityService.validateFile(file);

            //Generate Hash of fileData
            String fileDataHash = utilityService.generateFileHash(file.getBytes());

            //Check for duplicate fileData
            boolean fileExists = notesService.isFileDataExist(fileDataHash);
            if (fileExists) {
                return new ResponseEntity<>( "This file has already been uploaded!",
                        HttpStatus.BAD_REQUEST);
            }
            // Converting notesDTO to Notes
            Notes notes = dtoService.getNotes(notesDTO);
            notes.setFileDataHash(fileDataHash);

            //Getting uploader name
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            // Process and save notes and file
            Notes savedNotes = notesService.uploadNotes(notes, file, username);
            if(savedNotes!=null)
                return new ResponseEntity<>(dtoService.convertToNotesDTO(savedNotes),
                        HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Notes Upload Unsuccessful",
                        HttpStatus.SERVICE_UNAVAILABLE) ;
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/notes/download/1
    @Operation(summary = "Download notes by ID")
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadNotes(@PathVariable Integer id) {
        try {
            Notes notes = notesService.getNotes(id);
            if (notes != null) {
                String fileName = notes.getFileName();
                String fileType = notes.getFileType();
                byte[] fileData = notes.getFileData();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(fileType));
                headers.setContentDispositionFormData("attachment",fileName);
                return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
            } else
                return new ResponseEntity<>("May be this notes is not available!!",
                        HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
