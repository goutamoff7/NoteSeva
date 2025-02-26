package com.noteseva.repository;

import com.noteseva.model.Department;
import com.noteseva.model.Subject;
import com.noteseva.model.SubjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectAssignmentRepository extends JpaRepository<SubjectAssignment,Integer> {
    @Query("SELECT sa FROM SubjectAssignment sa " +
            "JOIN sa.department d " +
            "JOIN d.course c " +
            "JOIN sa.subject s " +
            "WHERE c.course = :course AND " +
            "d.department = :department " +
            "AND s.subject = :subject")
    SubjectAssignment findSubjectAssignment(String course,
                                            String department,
                                            String subject);

    @Query("SELECT sa FROM SubjectAssignment sa " +
            "JOIN sa.department d " +
            "JOIN sa.subject s " +
            "WHERE d.department = :department " +
            "AND s.subject = :subject")
    SubjectAssignment findSubjectAssignment(String department, String subject);
}
