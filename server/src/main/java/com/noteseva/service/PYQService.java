package com.noteseva.service;

import com.noteseva.model.PYQ;
import com.noteseva.repository.PYQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return pyqRepository.findById(id).orElse(null);
    }

    public PYQ uploadPYQ(PYQ pyq, MultipartFile file) throws IOException
    {
        pyq.setFileName(file.getOriginalFilename());
        pyq.setFileType(file.getContentType());
        pyq.setFileData(file.getBytes());
        return pyqRepository.save(pyq);
    }
}
