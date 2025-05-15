package com.noteseva.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="department_id")
    private Integer id;

    @NotBlank(message = "Please enter the Department")
    @Column(name="department_name",nullable = false,unique = true)
    private String departmentName;

    //relationship
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="course_id",nullable = false)
    private Course course;

    @JsonIgnore
    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
    private Set<SubjectAssignment> subjectAssignment;
}
