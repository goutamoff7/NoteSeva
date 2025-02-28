package com.noteseva.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="course_id")
    private Integer id;

    @NotBlank(message = "Please enter the Course")
    @Column(name="course_name",nullable = false,unique = true)
    private String courseName;

    //relationship
    @JsonIgnore
    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Set<Department> department;
}
