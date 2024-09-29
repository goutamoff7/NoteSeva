package com.noteseva.controller;

import com.noteseva.model.Organizer;
import com.noteseva.service.OrganizerService;
import com.noteseva.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
public class OrganizerController
{

    @Autowired
    OrganizerService organizerService;

    @GetMapping("/organizer")
    public List<Organizer> getAllOrganizer()
    {
        return organizerService.getAllOrganizer();
    }

    @GetMapping("/organizer/{id}")
    public Organizer getOrganizer(@PathVariable Integer id)
    {
        return organizerService.getOrganizer(id);

    }

    @PostMapping("/organizer")
    public Organizer addOrganizer(@RequestBody Organizer organizer)
    {
        return organizerService.addOrganizer(organizer);
    }
}
