package com.noteseva.controller;

import com.noteseva.model.Notes;
import com.noteseva.model.PYQ;
import com.noteseva.service.PYQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
public class PYQController
{

    @Autowired
    PYQService pyqService;

    @GetMapping("/pyq")
    public List<PYQ> getAllPYQ()
    {
        return pyqService.getAllPYQ();
    }

    @GetMapping("/pyq/{id}")
    public PYQ getPYQ(@PathVariable Integer id)
    {
        return pyqService.getPYQ(id);

    }

    @PostMapping("/pyq")
    public PYQ addPYQ(@RequestBody PYQ pyq)
    {
        return pyqService.addPYQ(pyq);
    }
}
