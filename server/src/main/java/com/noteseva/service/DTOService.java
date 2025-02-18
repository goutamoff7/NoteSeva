package com.noteseva.service;
import com.noteseva.DTO.NotesDTO;
import com.noteseva.DTO.OrganizerDTO;
import com.noteseva.DTO.PYQDTO;
import com.noteseva.DTO.UsersDTO;
import com.noteseva.model.Notes;
import com.noteseva.model.Organizer;
import com.noteseva.model.PYQ;
import com.noteseva.model.Users;
import org.springframework.stereotype.Service;

@Service
public class DTOService {

    public Users getUser(UsersDTO userDTO){
        Users user = new Users();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public Notes getNotes(NotesDTO notesDTO){
        Notes notes = new Notes();
        notes.setTopicName(notesDTO.getTopicName());
        notes.setSubjectDepartment(notesDTO.getSubjectDepartment());
        notes.setFileName(notesDTO.getFileName());
        notes.setFileType(notesDTO.getFileType());
        notes.setFileData(notesDTO.getFileData());
        return notes;
    }

    public Organizer getOrganizer(OrganizerDTO organizerDTO){
        Organizer organizer = new Organizer();
        organizer.setYear(organizerDTO.getYear());
        organizer.setSubjectDepartment(organizerDTO.getSubjectDepartment());
        organizer.setFileName(organizerDTO.getFileName());
        organizer.setFileType(organizerDTO.getFileType());
        organizer.setFileData(organizerDTO.getFileData());
        return organizer;
    }

    public PYQ getPYQ(PYQDTO pyqDTO){
        PYQ pyq = new PYQ();
        pyq.setYear(pyqDTO.getYear());
        pyq.setSubjectDepartment(pyqDTO.getSubjectDepartment());
        pyq.setFileName(pyqDTO.getFileName());
        pyq.setFileType(pyqDTO.getFileType());
        pyq.setFileData(pyqDTO.getFileData());
        return pyq;
    }
}
