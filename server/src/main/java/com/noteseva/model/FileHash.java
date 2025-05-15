package com.noteseva.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "file_hash")
public class FileHash {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="hash_id")
    private Integer id;

    @Column(name = "file_data_hash", nullable = false, unique = true)
    private String fileDataHash;
}
