package com.noteseva.controller;

import com.noteseva.DTO.OrganizerDTO;
import com.noteseva.Pagination.PageResponse;
import com.noteseva.model.FileHash;
import com.noteseva.model.Organizer;
import com.noteseva.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("organizer")
@Tag(name = "Organizer APIs", description = "View, Search, Upload and Download Organizer")
public class OrganizerController {

    @Autowired
    OrganizerService organizerService;

    @Autowired
    UtilityService utilityService;

    @Autowired
    FileHashService fileHashService;

    @Autowired
    DTOService dtoService;

    @Autowired
    UserService userService;

    //localhost:8080/organizer/all?
    // courseName=BTECH &
    // departmentName=CSE &
    // subjectName=Basic Electrical Engineering
    // pageNumber=0&
    // pageSize=12&
    // sortBy=id&
    // sortingOrder=ASC
    @Operation(summary = "Fetch all Organizer")
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrganizer(
            @RequestParam String courseName,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "8") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortingOrder) {
        try {
            PageResponse<OrganizerDTO> organizerDTOPageResponse = organizerService
                    .getAllOrganizer(courseName, departmentName, subjectName,
                            pageNumber, pageSize, sortBy, sortingOrder);
            if (organizerDTOPageResponse == null)
                return new ResponseEntity<>("May be Organizers are not available",
                        HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(organizerDTOPageResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/organizer/upload
    @Operation(summary = "Upload organizer")
    @Transactional
    @PostMapping("/upload")
    public ResponseEntity<?> uploadNotes(@RequestPart @Valid OrganizerDTO organizerDTO,
                                         @RequestPart MultipartFile file) {
        try {
            // Validate the file
            utilityService.validateFile(file);

            //Generate Hash of fileData
            String fileDataHash = fileHashService.generateFileDataHash(file.getBytes());

            //Check for duplicate fileData
            boolean fileExists = fileHashService.isFileDataHashExist(fileDataHash);
            if (fileExists) {
                return new ResponseEntity<>("This file has already been uploaded!",
                        HttpStatus.BAD_REQUEST);
            }

            // Converting organizerDTO to organizer
            Organizer organizer = dtoService.getOrganizer(organizerDTO);

            //Getting uploader name
            String username = userService.getCurrentUsername();;

            // Process and save notes and fileDataHash
            Organizer savedOrganizer = organizerService.uploadOrganizer(organizer, file, username);
            FileHash savedFileDataHash = fileHashService.save(fileDataHash);
            if (savedOrganizer != null && savedFileDataHash != null)
                return new ResponseEntity<>(dtoService.convertToOrganizerDTO(savedOrganizer),
                        HttpStatus.CREATED);
            return new ResponseEntity<>("Organizer Upload Unsuccessful",
                    HttpStatus.SERVICE_UNAVAILABLE);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/organizer/get/1?option=view
    //localhost:8080/organizer/get/1?option=download
    @Operation(summary = "View or Download organizer by ID")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getOrganizer(@PathVariable Integer id,
                                          @RequestParam(required = false, defaultValue = "view") String option) {
        try {
            Organizer organizer = organizerService.getOrganizer(id);
            if (organizer != null) {
                String fileName = organizer.getFileName();
                String fileType = organizer.getFileType();
                byte[] fileData = organizer.getFileData();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(fileType));
                if (option.equals("download"))
                    headers.setContentDispositionFormData("attachment", fileName);
                return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
            }
            return new ResponseEntity<>("Maybe this Organizer is not available!!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
