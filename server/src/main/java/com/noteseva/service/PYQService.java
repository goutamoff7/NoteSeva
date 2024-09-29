package com.noteseva.service;

import com.noteseva.model.Notes;
import com.noteseva.model.PYQ;
import com.noteseva.repository.PYQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PYQService
{
    @Autowired
    PYQRepository pyqRepository;

    public List<PYQ> getAllPYQ()
    {
        return pyqRepository.findAll();
    }

    public PYQ getPYQ(Integer id)
    {
        return pyqRepository.findById(id).get();
    }

    public PYQ addPYQ(PYQ pyq)
    {
        return pyqRepository.save(pyq);
    }
}
