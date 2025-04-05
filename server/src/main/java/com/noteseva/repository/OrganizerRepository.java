package com.noteseva.repository;

import com.noteseva.model.Organizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer,Integer> {
    boolean existsByFileDataHash(String fileDataHash);

    @Query("SELECT n from Organizer n " +
            "JOIN n.subjectAssignment sa " +
            "JOIN sa.department d " +
            "JOIN d.course c " +
            "JOIN sa.subject s " +
            "WHERE c.courseName = :courseName AND " +
            "(:departmentName IS NULL OR d.departmentName = :departmentName) AND " +
            "(:subjectName IS NULL OR s.subjectName = :subjectName)"
    )
    Page<Organizer> getAllOrganizer(String courseName,
                                    String departmentName,
                                    String subjectName,
                                    Pageable pageable);
}
