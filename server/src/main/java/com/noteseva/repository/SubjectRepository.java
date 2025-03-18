package com.noteseva.repository;

import com.noteseva.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    Subject findBySubjectName(String subjectName);
}
