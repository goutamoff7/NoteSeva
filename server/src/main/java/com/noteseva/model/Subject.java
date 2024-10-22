package com.noteseva.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Subject
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String subjectCode;
    private String subjectName;
    private String department;
    private String courseName;
}
