package com.noteseva.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubjectAssignmentDTO {

    private Integer id;

    @NotBlank(message = "Course name can't be blank.")
    private String courseName;

    @NotBlank(message = "Department name can't be blank.")
    private String departmentName;

    @NotBlank(message = "Subject name can't be blank.")
    private String subjectName;

    @NotBlank(message = "Subject code can't be blank.")
    @Column(name="subject_code",nullable = false,unique = true)
    private String subjectCode;
}
