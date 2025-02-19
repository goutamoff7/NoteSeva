package com.noteseva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subject
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="subject_id")
    private Integer id;

    @NotBlank
    @Column(name="subject_code",nullable = false,unique = true)
    private String subjectCode;

    @NotBlank
    @Column(name="subject_name",nullable = false)
    private String subject;

    //relationship
    @OneToMany(mappedBy = "subject",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Set<SubjectDepartment> subjectDepartment;
}
