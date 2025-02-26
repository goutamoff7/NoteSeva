package com.noteseva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @NotBlank(message = "Please enter the Course")
    @Column(name="course",nullable = false,unique = true)
    private String course;

    //relationship
    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Set<Department> department;
}
