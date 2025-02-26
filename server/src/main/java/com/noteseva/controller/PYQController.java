package com.noteseva.controller;
import com.noteseva.DTO.PYQDTO;
import com.noteseva.model.PYQ;
import com.noteseva.service.DTOService;
import com.noteseva.service.PYQService;
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

@RestController
@RequestMapping("pyq")
@Tag(name="Previous Year Question APIs",description = "View, Search, Upload and Download Previous Year Question")
public class PYQController {

    @Autowired
    PYQService pyqService;

    @Autowired
    UtilityService utilityService;

    @Autowired
    DTOService dtoService;

    //localhost:8080/pyq/all
    @Operation(summary = "")
    @GetMapping("/all")
    public ResponseEntity<?> getAllPYQ() {
        try {
            return new ResponseEntity<>(pyqService.getAllPYQ(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("May be Question Papers are not available!!", HttpStatus.NOT_FOUND);
        }
    }

    //localhost:8080/pyq/1
    @Operation(summary = "")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPYQ(@PathVariable Integer id) {
        try {
            PYQ pyq = pyqService.getPYQ(id);
            if (pyq != null)
                return new ResponseEntity<>(pyq, HttpStatus.OK);
            else
                return new ResponseEntity<>("May be Question Papers are not available!!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/pyq/upload
    @Operation(summary = "")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadPYQ(@RequestPart @Valid PYQDTO pyqDTO,
                                       @RequestPart MultipartFile file) {
        try {
            PYQ pyq = dtoService.getPYQ(pyqDTO);
            // Validate the file
            utilityService.validateFile(file);

            //Getting uploader name
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            // Process and save pyq and file
            PYQ pyq1 = pyqService.uploadPYQ(pyq, file, username);
            return new ResponseEntity<>(pyqDTO, HttpStatus.CREATED);

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/pyq/download/1
    @Operation(summary = "")
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadPYQ(@PathVariable Integer id) {
        try {
            PYQ pyq = pyqService.getPYQ(id);
            if (pyq != null) {
                byte[] fileData = pyq.getFileData();
                String fileType = pyq.getFileType();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(fileType));
                return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
            } else
                return new ResponseEntity<>("May be Question Papers are not available!!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
