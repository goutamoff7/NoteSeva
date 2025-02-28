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
        return new NotesDTO(
                notes.getSubjectAssignment().getDepartment().getCourse().getCourseName(),  // Extract Course Name
                notes.getSubjectAssignment().getDepartment().getDepartmentName(),  // Extract Department Name
                notes.getSubjectAssignment().getSubject().getSubjectName(),  // Extract Subject Name
                notes.getTopicName(),
                notes.getFileName(),
                notes.getFileType()
        );
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
        return new OrganizerDTO(
                organizer.getYear(),
                organizer.getSubjectAssignment().getDepartment().getCourse().getCourseName(),  // Extract Course Name
                organizer.getSubjectAssignment().getDepartment().getDepartmentName(),  // Extract Department Name
                organizer.getSubjectAssignment().getSubject().getSubjectName(),  // Extract Subject Name
                organizer.getFileName(),
                organizer.getFileType()
        );
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
        return new PYQDTO(
                pyq.getYear(),
                pyq.getSubjectAssignment().getDepartment().getCourse().getCourseName(),  // Extract Course Name
                pyq.getSubjectAssignment().getDepartment().getDepartmentName(),  // Extract Department Name
                pyq.getSubjectAssignment().getSubject().getSubjectName(),  // Extract Subject Name
                pyq.getFileName(),
                pyq.getFileType()
        );
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

    public SubjectAssignment findSubjectAssignment(SubjectAssignmentDTO subjectAssignmentDTO) {
        SubjectAssignment subjectAssignment =
                subjectAssignmentRepository
                        .findSubjectAssignment(
                                subjectAssignmentDTO.getCourseName(),
                                subjectAssignmentDTO.getDepartmentName(),
                                subjectAssignmentDTO.getSubjectName()
                        );
        return subjectAssignment;
    }
}
