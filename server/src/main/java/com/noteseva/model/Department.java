package com.noteseva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="department_id")
    private Integer id;

    @NotBlank(message = "Please enter the Department")
    @Column(name="department",nullable = false,unique = true)
    private String department;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="course_id",nullable = false)
    private Course course;

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
    private Set<SubjectDepartment> subjectDepartment;
}
