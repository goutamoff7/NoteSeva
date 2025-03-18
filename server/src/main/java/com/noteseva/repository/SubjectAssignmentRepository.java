package com.noteseva.repository;

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
            "WHERE c.courseName = :courseName AND " +
            "d.departmentName = :departmentName " +
            "AND s.subjectName = :subjectName")
    SubjectAssignment findSubjectAssignment(String courseName,
                                            String departmentName,
                                            String subjectName);

    @Query("SELECT sa FROM SubjectAssignment sa " +
            "JOIN sa.department d " +
            "JOIN sa.subject s " +
            "WHERE d.departmentName = :departmentName " +
            "AND s.subjectName = :subjectName")
    SubjectAssignment findSubjectAssignment(String departmentName, String subjectName);
}
