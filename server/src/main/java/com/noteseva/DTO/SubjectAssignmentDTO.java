package com.noteseva.DTO;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectAssignmentDTO {

    @NotBlank(message = "Course can't be blank.")
    private String course;

    @NotBlank(message = "Department can't be blank.")
    private String department;

    @NotBlank(message = "Subject can't be blank.")
    private String subject;

    @NotBlank(message = "Subject code can't be blank.")
    @Column(name="subject_code",nullable = false,unique = true)
    private String subjectCode;
}
