package com.noteseva.repository;

import com.noteseva.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    Course findByCourse(String course);
}
