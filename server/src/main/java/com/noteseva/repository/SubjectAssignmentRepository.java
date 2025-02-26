package com.noteseva.repository;

import com.noteseva.model.SubjectDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectDepartmentRepository extends JpaRepository<SubjectDepartment,Integer> {
    @Query("SELECT sd FROM SubjectDepartment sd " +
            "JOIN sd.department d " +
            "JOIN d.course c " +
            "JOIN sd.subject s " +
            "WHERE c.course = :course AND " +
            "d.department = :department " +
            "AND s.subject = :subject")
    SubjectDepartment findSubjectDepartment(String course,
                                            String department,
                                            String subject);
}
