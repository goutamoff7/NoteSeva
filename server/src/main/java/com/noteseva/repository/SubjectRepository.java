package com.noteseva.repository;

import com.noteseva.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {

    //JPQL
//    @Query("SELECT s from Subject s WHERE "+
//            "LOWER(s.subject) LIKE LOWER(CONCAT('%', :keyword, '%'))")
//    List<Subject> searchSubject(String keyword);

//    @Query("SELECT s.subject " +
//            "FROM SubjectDepartment sd " +
//            "JOIN sd.subject s " +
//            "JOIN sd.department d " +
//            "WHERE d.department = :department")
//    List<Subject> getSubjects(String department);
}
