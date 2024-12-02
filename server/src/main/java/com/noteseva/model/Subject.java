package com.noteseva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subject
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    @Column(name="subject_code",nullable = false,unique = true)
    private String subjectCode;

    @NotBlank
    @Column(name="subject_name",nullable = false)
    private String subjectName;

    @NotBlank
    @Column(nullable = false)
    private String department;

    @NotBlank
    @Column(name="course_name",nullable = false)
    private String courseName;
}
