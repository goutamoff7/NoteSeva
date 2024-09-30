package com.noteseva.controller;

import com.noteseva.model.Notes;
import com.noteseva.service.NotesService;
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
public class NotesController
{

    @Autowired
    NotesService notesService;

    @GetMapping("/")
    public String greet()
    {
        return "Welcome to NoteSeva";
    }

    @GetMapping("/notes")
    public ResponseEntity<?> getAllNotes()
    {
        try {
            return new ResponseEntity<>(notesService.getAllNotes(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("May be Notes are not available",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<?> getNotes(@PathVariable Integer id)
    {
        try {
            Notes notes = notesService.getNotes(id);
            if (notes != null)
                return new ResponseEntity<>(notes, HttpStatus.OK);
            else
                throw new Exception("May be this notes is not available!!");

        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/uploadNotes")
    public ResponseEntity<?> uploadNotes(@RequestPart Notes notes,
                          @RequestPart MultipartFile file)
    {
        try
        {
            Notes notes1 = notesService.uploadNotes(notes,file);
            return new ResponseEntity<>(notes1,HttpStatus.CREATED);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
           return new ResponseEntity<>("Something went wrong!!",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/downloadNotes/{id}")
    public ResponseEntity<?> downloadNotes(@PathVariable Integer id){
        try{
            Notes notes=notesService.getNotes(id);
            if(notes!=null) {
                byte[] fileData = notes.getFileData();
                String fileType = notes.getFileType();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(fileType));
                return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
            }
            else
                throw new Exception("May be this notes is not available!!");
        }catch(Exception e)
            {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
            }


    }
}
