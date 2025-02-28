package com.noteseva.repository;

import com.noteseva.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends JpaRepository<Notes,Integer> {

    public boolean existsByFileDataHash(String fileDataHash);
}
