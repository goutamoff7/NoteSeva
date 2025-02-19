package com.noteseva.service;
import com.noteseva.DTO.NotesDTO;
import com.noteseva.DTO.OrganizerDTO;
import com.noteseva.DTO.PYQDTO;
import com.noteseva.DTO.UsersDTO;
import com.noteseva.model.*;
import com.noteseva.repository.SubjectDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DTOService {

    @Autowired
    SubjectDepartmentRepository subjectDepartmentRepository;

    public Users getUser(UsersDTO userDTO){
        Users user = new Users();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public Notes getNotes(NotesDTO notesDTO){
        Notes notes = new Notes();
        SubjectDepartment subjectDepartment = subjectDepartmentRepository
                .findSubjectDepartment(
                notesDTO.getCourse(),
                notesDTO.getDepartment(),
                notesDTO.getSubject()
        );
        notes.setSubjectDepartment(subjectDepartment);
        notes.setTopic(notesDTO.getTopic());
        notes.setFileName(notesDTO.getFileName());
        notes.setFileType(notesDTO.getFileType());
        notes.setFileData(notesDTO.getFileData());
        return notes;
    }

    public Organizer getOrganizer(OrganizerDTO organizerDTO){
        Organizer organizer = new Organizer();
        SubjectDepartment subjectDepartment = subjectDepartmentRepository
                .findSubjectDepartment(
                organizerDTO.getCourse(),
                organizerDTO.getDepartment(),
                organizerDTO.getSubject()
        );
        organizer.setSubjectDepartment(subjectDepartment);
        organizer.setYear(organizerDTO.getYear());
        organizer.setFileName(organizerDTO.getFileName());
        organizer.setFileType(organizerDTO.getFileType());
        organizer.setFileData(organizerDTO.getFileData());
        return organizer;
    }

    public PYQ getPYQ(PYQDTO pyqDTO){
        PYQ pyq = new PYQ();
        SubjectDepartment subjectDepartment = subjectDepartmentRepository
                .findSubjectDepartment(
                        pyqDTO.getCourse(),
                        pyqDTO.getDepartment(),
                        pyqDTO.getSubject()
                );
        pyq.setSubjectDepartment(subjectDepartment);
        pyq.setYear(pyqDTO.getYear());
        pyq.setFileName(pyqDTO.getFileName());
        pyq.setFileType(pyqDTO.getFileType());
        pyq.setFileData(pyqDTO.getFileData());
        return pyq;
    }
}
