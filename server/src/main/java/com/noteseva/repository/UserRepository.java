package com.noteseva.repository;

import com.noteseva.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {

    Users findByUsername(String username);

    Users findByEmail(String email);

    boolean existsByEmail(String email);
}
