package com.noteseva.controller;

import com.noteseva.DTO.OrganizerDTO;
import com.noteseva.model.Organizer;
import com.noteseva.service.DTOService;
import com.noteseva.service.OrganizerService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("organizer")
@Tag(name="Organizer APIs",description = "View, Search, Upload and Download Organizer")
public class OrganizerController {

    @Autowired
    OrganizerService organizerService;

    @Autowired
    UtilityService utilityService;

    @Autowired
    DTOService dtoService;

    //localhost:8080/organizer/all
    @Operation(summary = "Fetch all Organizer")
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrganizer() {
        try {
            List<OrganizerDTO> organizerDTOList = organizerService.getAllOrganizer()
                    .stream()
                    .map(dtoService::convertToOrganizerDTO) // Convert Notes -> OrganizerDTO
                    .toList();
            if (organizerDTOList.isEmpty()) {
                return new ResponseEntity<>("May be Organizers are not available",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(organizerDTOList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/organizer/1
    @Operation(summary = "Fetch organizer by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizer(@PathVariable Integer id) {
        try {
            Organizer organizer = organizerService.getOrganizer(id);
            if (organizer != null)
                return new ResponseEntity<>(dtoService.convertToOrganizerDTO(organizer), HttpStatus.OK);
            else
                return new ResponseEntity<>("May be this Organizer is not available!!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/organizer/upload
    @Operation(summary = "Upload organizer")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadNotes(@RequestPart @Valid OrganizerDTO organizerDTO,
                                         @RequestPart MultipartFile file) {
        try {
            // Validate the file
            utilityService.validateFile(file);

            //Generate Hash of fileData
            String fileDataHash = utilityService.generateFileHash(file.getBytes());

            //Check for duplicate fileData
            boolean fileExists = organizerService.isFileDataExist(fileDataHash);
            if (fileExists) {
                return new ResponseEntity<>( "This file has already been uploaded!",HttpStatus.BAD_REQUEST);
            }

            // Converting organizerDTO to organizer
            Organizer organizer = dtoService.getOrganizer(organizerDTO);
            organizer.setFileDataHash(fileDataHash);

            //Getting uploader name
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            // Process and save notes and file
            Organizer savedOrganizer = organizerService.uploadOrganizer(organizer, file, username);
            if(savedOrganizer!=null)
                return new ResponseEntity<>(dtoService.convertToOrganizerDTO(savedOrganizer), HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Organizer Upload Unsuccessful",HttpStatus.SERVICE_UNAVAILABLE) ;
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/organizer/download/1
    @Operation(summary = "Download organizer by ID")
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadNotes(@PathVariable Integer id) {
        try {
            Organizer organizer = organizerService.getOrganizer(id);
            if (organizer != null) {
                String fileName = organizer.getFileName();
                String fileType = organizer.getFileType();
                byte[] fileData = organizer.getFileData();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(fileType));
                headers.setContentDispositionFormData("attachment",fileName);
                return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
            } else
                return new ResponseEntity<>("May be this Organizer is not available!!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
