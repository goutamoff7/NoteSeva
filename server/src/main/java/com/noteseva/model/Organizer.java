package com.noteseva.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Organizer
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int year;
    private String sharedBy;
    private LocalDate date;
    private String subjectName;
    private String department;
    private String courseName;

    //file handling
    private String fileName;
    private String fileType;
    private byte[] fileData;

    //verification
    private boolean valid;
}
