package com.noteseva.repository;

import com.noteseva.model.PYQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PYQRepository extends JpaRepository<PYQ,Integer> {

    @Query("SELECT n from PYQ n " +
            "JOIN n.subjectAssignment sa " +
            "JOIN sa.department d " +
            "JOIN d.course c " +
            "JOIN sa.subject s " +
            "WHERE c.courseName = :courseName AND " +
            "(:departmentName IS NULL OR d.departmentName = :departmentName) AND " +
            "(:subjectName IS NULL OR s.subjectName = :subjectName)"
    )
    Page<PYQ> getAllPYQ(String courseName,
                        String departmentName,
                        String subjectName,
                        Pageable pageable);
}
