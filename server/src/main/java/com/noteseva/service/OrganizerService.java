package com.noteseva.service;

import com.noteseva.model.Organizer;
import com.noteseva.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return organizerRepository.findById(id).orElse(null);
    }

    public Organizer uploadOrganizer(Organizer organizer)
    {
        return organizerRepository.save(organizer);
    }

    public Organizer uploadOrganizer(Organizer organizer, MultipartFile file){
        try {
            organizer.setFileName(file.getOriginalFilename());
            organizer.setFileType(file.getContentType());
            organizer.setFileData(file.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage()+"from service class.");
        }
        return organizerRepository.save(organizer);
    }
}
