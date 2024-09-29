package com.noteseva.controller;

import com.noteseva.model.Notes;
import com.noteseva.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Notes> getAllNotes()
    {
        return notesService.getAllNotes();
    }

    @GetMapping("/notes/{id}")
    public Notes getNotes(@PathVariable Integer id)
    {
        return notesService.getNotes(id);

    }

    @PostMapping("/notes")
    public Notes addNotes(@RequestBody Notes notes)
    {
        return notesService.addNotes(notes);
    }
}
