package com.noteseva.controller;

import com.noteseva.model.Organizer;
import com.noteseva.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@CrossOrigin
@RestController
public class OrganizerController
{

    @Autowired
    OrganizerService organizerService;

    //localhost:8080/organizer
    @GetMapping("/organizer")
    public ResponseEntity<?> getAllOrganizer()
    {
        try {
            return new ResponseEntity<>(organizerService.getAllOrganizer(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("May be Organizers are not available!!",HttpStatus.NOT_FOUND);
        }
    }

    //localhost:8080/organizer/1
    @GetMapping("/organizer/{id}")
    public ResponseEntity<?> getOrganizer(@PathVariable Integer id)
    {
        try{
            Organizer organizer = organizerService.getOrganizer(id);
            if (organizer!=null)
                return new ResponseEntity<>(organizer,HttpStatus.OK);
            else
                return new ResponseEntity<>("May be this organizer is not available!!",HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/uploadOrganizer
    @PostMapping("/uploadOrganizer")
    public ResponseEntity<?> uploadOrganizer(@RequestPart Organizer organizer,
                                             @RequestPart MultipartFile file)
    {
        try{
            Organizer organizer1 = organizerService.uploadOrganizer(organizer,file);
            return  new ResponseEntity<>(organizer1,HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return  new ResponseEntity<>("Something Went Wrong!!",HttpStatus.BAD_REQUEST);
        }
    }

    //localhost:8080/downloadOrganizer/1
    @GetMapping("/downloadOrganizer/{id}")
    public ResponseEntity<?> downloadOrganizer(@PathVariable Integer id)
    {
        try{
            Organizer organizer = organizerService.getOrganizer(id);
            if (organizer!=null)
            {
                byte[] fileData = organizer.getFileData();
                String fileType = organizer.getFileType();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(fileType));
                return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
            }
            else
                return new ResponseEntity<>("May be this organizer is not available!!",HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
