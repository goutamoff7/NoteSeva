package com.noteseva.controller;

import com.noteseva.model.PYQ;
import com.noteseva.service.PYQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
public class PYQController
{

    @Autowired
    PYQService pyqService;

    @GetMapping("/pyq")
    public ResponseEntity<?> getAllPYQ()
    {
        try {
            return new ResponseEntity<>(pyqService.getAllPYQ(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("May be Question Papers are not available!!",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pyq/{id}")
    public ResponseEntity<?> getPYQ(@PathVariable Integer id)
    {
        try {
            PYQ pyq = pyqService.getPYQ(id);
            if(pyq!=null)
            {
                return new ResponseEntity<>(pyq,HttpStatus.OK);
            }
            else
            {
                throw new Exception("May be Question Papers are not available!!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/uploadPYQ")
    public ResponseEntity<?> uploadPYQ(@RequestPart PYQ pyq,
                         @RequestPart MultipartFile file)
    {
        try{
            PYQ pyq1 = pyqService.uploadPYQ(pyq,file);
            return new ResponseEntity<>(pyq1,HttpStatus.CREATED);
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong!!",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/downloadPYQ/{id}")
    public ResponseEntity<?> downloadPYQ(@PathVariable Integer id)
    {
        try{
            PYQ pyq = pyqService.getPYQ(id);
            if(pyq!=null)
            {
                byte[] fileData = pyq.getFileData();
                String fileType = pyq.getFileType();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(fileType));
                return new ResponseEntity<>(fileData,headers,HttpStatus.OK);
            }
            else
                throw new Exception("May be Question Papers are not available!!");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
