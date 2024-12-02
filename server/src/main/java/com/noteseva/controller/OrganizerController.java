package com.noteseva.controller;
import com.noteseva.model.Organizer;
import com.noteseva.service.OrganizerService;
import com.noteseva.service.UtilityService;
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

@CrossOrigin
@RestController
@RequestMapping("organizer")
public class OrganizerController {

    @Autowired
    OrganizerService organizerService;

    @Autowired
    UtilityService utilityService;

    //localhost:8080/organizer/all
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrganizer() {
        try {
            return new ResponseEntity<>(organizerService.getAllOrganizer(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("May be Organizers are not available!!", HttpStatus.NOT_FOUND);
        }
    }

    //localhost:8080/organizer/1
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizer(@PathVariable Integer id) {
        try {
            Organizer organizer = organizerService.getOrganizer(id);
            if (organizer != null)
                return new ResponseEntity<>(organizer, HttpStatus.OK);
            else
                return new ResponseEntity<>("May be this organizer is not available!!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/organizer/upload
    @PostMapping("/upload")
    public ResponseEntity<?> uploadOrganizer(@RequestPart @Valid Organizer organizer,
                                             @RequestPart MultipartFile file) {
        try {
            // Validate the file
            utilityService.validateFile(file);

            //Getting uploader name
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            // Process and save organizer and file
            Organizer organizer1 = organizerService.uploadOrganizer(organizer, file, username);
            return new ResponseEntity<>(organizer1, HttpStatus.CREATED);

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/organizer/download/1
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadOrganizer(@PathVariable Integer id) {
        try {
            Organizer organizer = organizerService.getOrganizer(id);
            if (organizer != null) {
                byte[] fileData = organizer.getFileData();
                String fileType = organizer.getFileType();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(fileType));
                return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
            } else
                return new ResponseEntity<>("May be this organizer is not available!!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
