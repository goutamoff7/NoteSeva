package com.noteseva.controller;

import com.noteseva.model.Notes;
import com.noteseva.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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

    
    @GetMapping("/notes")
    public ResponseEntity<List<Notes>> getAllNotes()
    {
        return new ResponseEntity<>(notesService.getAllNotes(), HttpStatus.OK);
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<?> getNotes(@PathVariable Integer id)
    {
        Notes notes = notesService.getNotes(id);
        if(notes!=null)
            return new ResponseEntity<>(notes,HttpStatus.OK);
        else
            return new ResponseEntity<>("Notes Not Found",HttpStatus.NOT_FOUND);
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
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/downloadNotes/{id}")
    public ResponseEntity<?> downloadNotes(@PathVariable Integer id){
        Notes notes=notesService.getNotes(id);
        if(notes!=null) {
            byte[] fileData = notes.getFileData();
            String fileType = notes.getFileType();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(fileType));
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Notes Not Found",HttpStatus.NOT_FOUND);
    }
}
