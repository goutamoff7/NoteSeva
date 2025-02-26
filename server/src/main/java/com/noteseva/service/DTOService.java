package com.noteseva.service;

import com.noteseva.DTO.*;
import com.noteseva.model.*;
import com.noteseva.repository.CourseRepository;
import com.noteseva.repository.DepartmentRepository;
import com.noteseva.repository.SubjectAssignmentRepository;
import com.noteseva.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DTOService {

    @Autowired
    SubjectAssignmentRepository subjectAssignmentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    public Users getUser(UsersDTO userDTO) {
        Users user = new Users();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public Notes getNotes(NotesDTO notesDTO) {
        Notes notes = new Notes();
        SubjectAssignment subjectDepartment = subjectAssignmentRepository
                .findSubjectAssignment(
                        notesDTO.getCourse(),
                        notesDTO.getDepartment(),
                        notesDTO.getSubject()
                );
        notes.setSubjectAssignment(subjectDepartment);
        notes.setTopic(notesDTO.getTopic());
        notes.setFileName(notesDTO.getFileName());
        notes.setFileType(notesDTO.getFileType());
        notes.setFileData(notesDTO.getFileData());
        return notes;
    }

    public Organizer getOrganizer(OrganizerDTO organizerDTO) {
        Organizer organizer = new Organizer();
        SubjectAssignment subjectDepartment = subjectAssignmentRepository
                .findSubjectAssignment(
                        organizerDTO.getCourse(),
                        organizerDTO.getDepartment(),
                        organizerDTO.getSubject()
                );
        organizer.setSubjectAssignment(subjectDepartment);
        organizer.setYear(organizerDTO.getYear());
        organizer.setFileName(organizerDTO.getFileName());
        organizer.setFileType(organizerDTO.getFileType());
        organizer.setFileData(organizerDTO.getFileData());
        return organizer;
    }

    public PYQ getPYQ(PYQDTO pyqDTO) {
        PYQ pyq = new PYQ();
        SubjectAssignment subjectDepartment = subjectAssignmentRepository
                .findSubjectAssignment(
                        pyqDTO.getCourse(),
                        pyqDTO.getDepartment(),
                        pyqDTO.getSubject()
                );
        pyq.setSubjectAssignment(subjectDepartment);
        pyq.setYear(pyqDTO.getYear());
        pyq.setFileName(pyqDTO.getFileName());
        pyq.setFileType(pyqDTO.getFileType());
        pyq.setFileData(pyqDTO.getFileData());
        return pyq;
    }


    public Course getCourse(SubjectAssignmentDTO subjectAssignmentDTO) {
        Course course = courseRepository.findByCourse(subjectAssignmentDTO.getCourse());
        if (course != null)
            return course;
        else {
            Course newCourse = new Course();
            newCourse.setCourse(subjectAssignmentDTO.getCourse());
            return newCourse;
        }

    }

    public Department getDepartment(SubjectAssignmentDTO subjectAssignmentDTO) {
        Department department = departmentRepository.findByDepartment(subjectAssignmentDTO.getDepartment());
        if (department != null)
            return department;
        else {
            Department newDepartment = new Department();
            newDepartment.setDepartment(subjectAssignmentDTO.getDepartment());
            newDepartment.setCourse(getCourse(subjectAssignmentDTO));
            return newDepartment;
        }
    }

    public Subject getSubject(SubjectAssignmentDTO subjectAssignmentDTO) {
        Subject subject = subjectRepository.findBySubject(subjectAssignmentDTO.getSubject());
        if (subject != null)
            return subject;
        else {
            Subject newSubject = new Subject();
            newSubject.setSubjectCode(subjectAssignmentDTO.getSubjectCode());
            newSubject.setSubject(subjectAssignmentDTO.getSubject());
            return newSubject;
        }
    }

}
