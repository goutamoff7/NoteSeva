package com.noteseva.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "file_hash")
public class FileHash {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="hash_id")
    private Integer id;

    @Column(name = "file_data_hash", nullable = false, unique = true)
    private String fileDataHash;
}
