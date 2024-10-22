package com.noteseva.repository;

import com.noteseva.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {

    //JPQL
    @Query("SELECT s from Subject s WHERE "+
            "LOWER(s.subjectName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Subject> searchSubject(String keyword);

    @Query("SELECT s from Subject s WHERE "+
            "LOWER(s.department) LIKE LOWER(CONCAT('%', :department, '%'))")
    List<Subject> getSubjects(String department);
}
