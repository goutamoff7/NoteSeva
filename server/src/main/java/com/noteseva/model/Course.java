package com.noteseva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="course_id")
    private Integer id;

    @NotBlank(message = "Please enter the Course name")
    @Column(name="course_name",nullable = false,unique = true)
    private String courseName;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    private Set<Department> departments;
}
