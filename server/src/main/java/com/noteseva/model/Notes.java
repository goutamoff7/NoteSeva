package com.noteseva.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Notes
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String topicName;
    private String sharedBy;
    private LocalDate date;
    private String subjectName;
    private String department;
    private String courseName;

    //file handling
    private String fileName;
    private String fileType;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] fileData;

    //verification
    private boolean valid;
}