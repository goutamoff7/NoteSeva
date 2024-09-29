package com.noteseva.service;

import com.noteseva.model.Notes;
import com.noteseva.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService
{
    @Autowired
    NotesRepository notesRepository;


    public Notes addNotes(Notes notes)
    {
        return notesRepository.save(notes);
    }

    public List<Notes> getAllNotes()
    {
        return notesRepository.findAll();
    }

    public Notes getNotes(Integer id)
    {
        return notesRepository.findById(id).get();
    }
}
