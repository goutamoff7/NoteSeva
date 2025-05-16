package com.noteseva.service;

import com.noteseva.DTO.*;
import com.noteseva.model.*;
import com.noteseva.repository.CourseRepository;
import com.noteseva.repository.DepartmentRepository;
import com.noteseva.repository.SubjectAssignmentRepository;
import com.noteseva.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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
        notesDTO.setUploadDateTime(notes.getUploadDateTime());
        notesDTO.setSharedBy(notes.getUser().getName());
        notesDTO.setImageUrl(notes.getUser().getImageUrl());
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
        organizerDTO.setUploadDateTime(organizer.getUploadDateTime());
        organizerDTO.setSharedBy(organizer.getUser().getName());
        organizerDTO.setImageUrl(organizer.getUser().getImageUrl());
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
        pyqDTO.setUploadDateTime(pyq.getUploadDateTime());
        pyqDTO.setSharedBy(pyq.getUser().getName());
        pyqDTO.setImageUrl(pyq.getUser().getImageUrl());
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

    public UserSummaryDTO convertToUserSummaryDTO(Users user) {
        UserSummaryDTO userSummaryDTO = new UserSummaryDTO();
        userSummaryDTO.setName(user.getName());
        userSummaryDTO.setUsername(user.getUsername());
        userSummaryDTO.setGender(user.getGender());
        userSummaryDTO.setCollegeName(user.getCollegeName());
        userSummaryDTO.setImageUrl(user.getImageUrl());
        userSummaryDTO.setRegisteredAt(user.getRegisteredAt());
        userSummaryDTO.setLastLoginAt(user.getLastLoginAt());
        return userSummaryDTO;
    }

    public Query getQuery(QueryDTO queryDTO) {
        Query query = new Query();
        query.setFirstName(queryDTO.getFirstName());
        query.setLastName(queryDTO.getLastName());
        query.setEmail(queryDTO.getEmail());
        query.setPhoneNumber(queryDTO.getPhoneNumber());
        query.setQuery(queryDTO.getQuery());
        return query;
    }

    public UserDetailsDTO convertToUserDetailsDTO(Users user) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setId(user.getId());
        userDetailsDTO.setUsername(user.getUsername());
        userDetailsDTO.setRole(user.getRole());
        userDetailsDTO.setName(user.getName());
        userDetailsDTO.setEmail(user.getEmail());
        userDetailsDTO.setPassword(user.getPassword());
        userDetailsDTO.setRegisteredAt(user.getRegisteredAt());
        userDetailsDTO.setLastLoginAt(user.getLastLoginAt());
        userDetailsDTO.setGender(user.getGender());
        userDetailsDTO.setCollegeName(user.getCollegeName());
        userDetailsDTO.setImageUrl(user.getImageUrl());
        userDetailsDTO.setLinkedInUrl(user.getLinkedInUrl());
        userDetailsDTO.setGitHubUrl(user.getGitHubUrl());
        userDetailsDTO.setOtherUrl(user.getOtherUrl());
        return userDetailsDTO;
    }

    public UploadedDocsDTO convertToUploadedDocsDTO(Users user) {

        UploadedDocsDTO uploadedDocsDTO = new UploadedDocsDTO();

        Set<NotesDTO> notesDTOList = user.getNotes()
                .stream()
                .map(this::convertToNotesDTO)
                .collect(Collectors.toSet());

        uploadedDocsDTO.setNotesDTOList(notesDTOList);

        Set<OrganizerDTO> organizerDTOList = user.getOrganizer()
                .stream()
                .map(this::convertToOrganizerDTO)
                .collect(Collectors.toSet());

        uploadedDocsDTO.setOrganizerDTOList(organizerDTOList);

        Set<PYQDTO> pyqDTOList = user.getPyq()
                .stream()
                .map(this::convertToPYQDTO)
                .collect(Collectors.toSet());

        uploadedDocsDTO.setPyqDTOList(pyqDTOList);

        Set<NotesDTO> bookmarkedNotesDTO = user.getBookmarkedNotes()
                .stream()
                .map(this::convertToNotesDTO)
                .collect(Collectors.toSet());

        return uploadedDocsDTO;
    }

    public BookmarkedDocsDTO convertToBookmarkedDocsDTO(Users user){

        BookmarkedDocsDTO bookmarkedDocsDTO = new BookmarkedDocsDTO();

        Set<NotesDTO> bookmarkedNotesDTO = user.getBookmarkedNotes()
                .stream()
                .map(this::convertToNotesDTO)
                .collect(Collectors.toSet());

        bookmarkedDocsDTO.setBookmarkedNotesDTO(bookmarkedNotesDTO);

        Set<OrganizerDTO> bookmarkedOrganizerDTO = user.getBookmarkedOrganizer()
                .stream()
                .map(this::convertToOrganizerDTO)
                .collect(Collectors.toSet());

        bookmarkedDocsDTO.setBookmarkedOrganizerDTO(bookmarkedOrganizerDTO);

        Set<PYQDTO> bookmarkedPyqDTO = user.getBookmarkedPYQ()
                .stream()
                .map(this::convertToPYQDTO)
                .collect(Collectors.toSet());

        bookmarkedDocsDTO.setBookmarkedPyqDTO(bookmarkedPyqDTO);

        return bookmarkedDocsDTO;
    }

}