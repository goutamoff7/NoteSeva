package com.noteseva.controller;

import com.noteseva.DTO.PYQDTO;
import com.noteseva.Pagination.PageResponse;
import com.noteseva.model.FileHash;
import com.noteseva.model.PYQ;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("pyq")
@Tag(name = "Previous Year Question APIs", description = "View, Search, Upload and Download Previous Year Question")
public class PYQController {

    @Autowired
    PYQService pyqService;

    @Autowired
    UtilityService utilityService;

    @Autowired
    FileHashService fileHashService;

    @Autowired
    DTOService dtoService;

    @Autowired
    UserService userService;

    //localhost:8080/pyq/all?
    // courseName=BTECH &
    // departmentName=CSE &
    // subjectName=Basic Electrical Engineering
    // pageNumber=0&
    // pageSize=12&
    // sortBy=id&
    // sortingOrder=ASC
    @Operation(summary = "Fetch all PYQ")
    @GetMapping("/all")
    public ResponseEntity<?> getAllPYQ(
            @RequestParam String courseName,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "12") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortingOrder) {
        try {
            PageResponse<PYQDTO> organizerDTOPageResponse = pyqService
                    .getAllPYQ(courseName, departmentName, subjectName,
                            pageNumber, pageSize, sortBy, sortingOrder);
            if (organizerDTOPageResponse == null)
                return new ResponseEntity<>("May be PYQs are not available",
                        HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(organizerDTOPageResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/pyq/1
    @Operation(summary = "Fetch PYQ by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizer(@PathVariable Integer id) {
        try {
            PYQ pyq = pyqService.getPYQ(id);
            if (pyq != null)
                return new ResponseEntity<>(dtoService.convertToPYQDTO(pyq)
                        , HttpStatus.OK);
            return new ResponseEntity<>("May be this PYQ is not available!!"
                    , HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/pyq/upload
    @Operation(summary = "Upload PYQ")
    @Transactional
    @PostMapping("/upload")
    public ResponseEntity<?> uploadNotes(@RequestPart @Valid PYQDTO pyqDTO,
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

            // Converting pyqDTO to pyq
            PYQ pyq = dtoService.getPYQ(pyqDTO);

            //Getting uploader name
            String username = userService.getCurrentUsername();

            // Process and save notes and fileDataHash
            PYQ savedPYQ = pyqService.uploadPYQ(pyq, file, username);
            FileHash savedFileDataHash = fileHashService.save(fileDataHash);
            if (savedPYQ != null && savedFileDataHash != null)
                return new ResponseEntity<>(dtoService.convertToPYQDTO(savedPYQ),
                        HttpStatus.CREATED);
            return new ResponseEntity<>("PYQ Upload Unsuccessful",
                    HttpStatus.SERVICE_UNAVAILABLE);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/pyq/get/1?option=view
    //localhost:8080/pyq/get/1?option=download
    @Operation(summary = "View or Download PYQ by ID")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPYQ(@PathVariable Integer id,
                                    @RequestParam(required = false, defaultValue = "view") String option) {
        try {
            PYQ pyq = pyqService.getPYQ(id);
            if (pyq != null) {
                String fileName = pyq.getFileName();
                String fileType = pyq.getFileType();
                byte[] fileData = pyq.getFileData();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(fileType));
                if (option.equals("download")) {
                    headers.setContentDispositionFormData("attachment", fileName);
                }
                return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
            }
            return new ResponseEntity<>("Maybe this PYQ is not available!!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
