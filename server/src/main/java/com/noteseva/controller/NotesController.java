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

    //localhost:8080/notes
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

    //localhost:8080/notes/1
    @GetMapping("/notes/{id}")
    public ResponseEntity<?> getNotes(@PathVariable Integer id)
    {
        try {
            Notes notes = notesService.getNotes(id);
            if (notes != null)
                return new ResponseEntity<>(notes, HttpStatus.OK);
            else
                return new ResponseEntity<>("May be this notes is not available!!", HttpStatus.NOT_FOUND);
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/uploadNotes
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

    //localhost:8080/downloadNotes/1
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
                return new ResponseEntity<>("May be this notes is not available!!",HttpStatus.NOT_FOUND);
        }catch(Exception e)
            {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
}
