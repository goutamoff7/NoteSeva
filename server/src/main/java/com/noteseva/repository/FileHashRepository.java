package com.noteseva.repository;

import com.noteseva.model.FileHash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileHashRepository extends JpaRepository<FileHash,Integer> {

    boolean existsByFileDataHash(String fileDataHash);
}
