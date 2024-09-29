package com.noteseva.service;

import com.noteseva.model.Organizer;
import com.noteseva.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizerService
{
    @Autowired
    OrganizerRepository organizerRepository;

    public List<Organizer> getAllOrganizer()
    {
        return organizerRepository.findAll();
    }

    public Organizer getOrganizer(Integer id)
    {
        return organizerRepository.findById(id).get();
    }

    public Organizer addOrganizer(Organizer organizer)
    {
        return organizerRepository.save(organizer);
    }
}
