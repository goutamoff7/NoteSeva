package com.noteseva.repository;

import com.noteseva.model.PYQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PYQRepository extends JpaRepository<PYQ,Integer> {
}
