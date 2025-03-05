package com.noteseva.repository;

import com.noteseva.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Integer> {

    boolean existsByFileDataHash(String fileDataHash);

    @Query("SELECT n from Notes n " +
            "JOIN n.subjectAssignment sa " +
            "JOIN sa.department d " +
            "JOIN d.course c " +
            "JOIN sa.subject s " +
            "WHERE c.courseName = :courseName AND " +
            "(:departmentName IS NULL OR d.departmentName = :departmentName) AND " +
            "(:subjectName IS NULL OR s.subjectName = :subjectName)"
    )
    List<Notes> getAllNotes(String courseName,
                            String departmentName,
                            String subjectName);
}
