package com.noteseva.repository;

import com.noteseva.model.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer,Integer> {
    boolean existsByFileDataHash(String fileDataHash);
}
