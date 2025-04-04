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

    @Autowired
    UtilityService utilityService;

    public Users getUser(UsersDTO userDTO) {
        Users user = new Users();
        user.setName(userDTO.getName());
        user.setUsername(utilityService.extractUsernameFromEmail(userDTO.getEmail())); // username = substring of email id, before @ symbol
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public Notes getNotes(NotesDTO notesDTO) {
        Notes notes = new Notes();
        SubjectAssignment subjectAssignment = subjectAssignmentRepository
                .findSubjectAssignment(
                        notesDTO.getCourseName(),
                        notesDTO.getDepartmentName(),
                        notesDTO.getSubjectName()
                );
        if (subjectAssignment == null) {
            throw new RuntimeException("SubjectAssignment not found for given course/department/subject");
        }
        notes.setSubjectAssignment(subjectAssignment);
        notes.setTopicName(notesDTO.getTopicName());
        return notes;
    }

    public NotesDTO convertToNotesDTO(Notes notes) {
        NotesDTO notesDTO = new NotesDTO();
        notesDTO.setId(notes.getId());
        notesDTO.setCourseName(notes.getSubjectAssignment().getDepartment().getCourse().getCourseName());  // Extract Course Name
        notesDTO.setDepartmentName(notes.getSubjectAssignment().getDepartment().getDepartmentName());  // Extract Department Name
        notesDTO.setSubjectName(notes.getSubjectAssignment().getSubject().getSubjectName());  // Extract Subject Name
        notesDTO.setTopicName(notes.getTopicName());
        notesDTO.setFileName(notes.getFileName());
        notesDTO.setFileType(notes.getFileType());
        return notesDTO;
    }

    public Organizer getOrganizer(OrganizerDTO organizerDTO) {
        Organizer organizer = new Organizer();
        SubjectAssignment subjectAssignment = subjectAssignmentRepository
                .findSubjectAssignment(
                        organizerDTO.getCourseName(),
                        organizerDTO.getDepartmentName(),
                        organizerDTO.getSubjectName()
                );
        if (subjectAssignment == null) {
            throw new RuntimeException("SubjectAssignment not found for given course/department/subject");
        }
        organizer.setSubjectAssignment(subjectAssignment);
        organizer.setYear(organizerDTO.getYear());
        return organizer;
    }

    public OrganizerDTO convertToOrganizerDTO(Organizer organizer) {
        OrganizerDTO organizerDTO = new OrganizerDTO();
        organizerDTO.setId(organizer.getId());
        organizerDTO.setYear(organizer.getYear());
        organizerDTO.setCourseName(organizer.getSubjectAssignment().getDepartment().getCourse().getCourseName());  // Extract Course Name
        organizerDTO.setDepartmentName(organizer.getSubjectAssignment().getDepartment().getDepartmentName());  // Extract Department Name
        organizerDTO.setSubjectName(organizer.getSubjectAssignment().getSubject().getSubjectName());  // Extract Subject Name
        organizerDTO.setFileName(organizer.getFileName());
        organizerDTO.setFileType(organizer.getFileType());
        return organizerDTO;
    }

    public PYQ getPYQ(PYQDTO pyqDTO) {
        PYQ pyq = new PYQ();
        SubjectAssignment subjectAssignment = subjectAssignmentRepository
                .findSubjectAssignment(
                        pyqDTO.getCourseName(),
                        pyqDTO.getDepartmentName(),
                        pyqDTO.getSubjectName()
                );
        if (subjectAssignment == null) {
            throw new RuntimeException("SubjectAssignment not found for given course/department/subject");
        }
        pyq.setSubjectAssignment(subjectAssignment);
        pyq.setYear(pyqDTO.getYear());
        return pyq;
    }

    public PYQDTO convertToPYQDTO(PYQ pyq) {
        PYQDTO pyqDTO = new PYQDTO();
        pyqDTO.setId(pyq.getId());
        pyqDTO.setYear(pyq.getYear());
        pyqDTO.setCourseName(pyq.getSubjectAssignment().getDepartment().getCourse().getCourseName());  // Extract Course Name
        pyqDTO.setDepartmentName(pyq.getSubjectAssignment().getDepartment().getDepartmentName());  // Extract Department Name
        pyqDTO.setSubjectName(pyq.getSubjectAssignment().getSubject().getSubjectName());  // Extract Subject Name
        pyqDTO.setFileName(pyq.getFileName());
        pyqDTO.setFileType(pyq.getFileType());
        return pyqDTO;
    }

    public Course getCourse(SubjectAssignmentDTO subjectAssignmentDTO) {
        Course course = courseRepository.findByCourseName(subjectAssignmentDTO.getCourseName());
        if (course != null)
            return course;
        else {
            Course newCourse = new Course();
            newCourse.setCourseName(subjectAssignmentDTO.getCourseName());
            return newCourse;
        }

    }

    public Department getDepartment(SubjectAssignmentDTO subjectAssignmentDTO) {
        Department department = departmentRepository.findByDepartmentName(subjectAssignmentDTO.getDepartmentName());
        if (department != null)
            return department;
        else {
            Department newDepartment = new Department();
            newDepartment.setDepartmentName(subjectAssignmentDTO.getDepartmentName());
            newDepartment.setCourse(getCourse(subjectAssignmentDTO));
            return newDepartment;
        }
    }

    public Subject getSubject(SubjectAssignmentDTO subjectAssignmentDTO) {
        Subject subject = subjectRepository.findBySubjectName(subjectAssignmentDTO.getSubjectName());
        if (subject != null)
            return subject;
        else {
            Subject newSubject = new Subject();
            newSubject.setSubjectCode(subjectAssignmentDTO.getSubjectCode());
            newSubject.setSubjectName(subjectAssignmentDTO.getSubjectName());
            return newSubject;
        }
    }

    public SubjectAssignmentDTO convertToSubjectAssignmentDTO(SubjectAssignment subjectAssignment) {
        SubjectAssignmentDTO subjectAssignmentDTO = new SubjectAssignmentDTO();
        subjectAssignmentDTO.setId(subjectAssignment.getId());  // Extract Course Name
        subjectAssignmentDTO.setCourseName(subjectAssignment.getDepartment().getCourse().getCourseName());  // Extract Course Name
        subjectAssignmentDTO.setDepartmentName(subjectAssignment.getDepartment().getDepartmentName());  // Extract Department Name
        subjectAssignmentDTO.setSubjectName(subjectAssignment.getSubject().getSubjectName()); // Extract Subject Name
        subjectAssignmentDTO.setSubjectCode(subjectAssignment.getSubject().getSubjectCode()); // Extract Subject Code
        return subjectAssignmentDTO;
    }
}


