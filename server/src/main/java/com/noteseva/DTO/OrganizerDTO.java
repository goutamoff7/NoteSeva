package com.noteseva.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrganizerDTO {

    private Integer id;

    @NotBlank(message = "Please choose any Year")
    private String year;

    //subject selection
    @NotBlank(message = "Course name can't be blank.")
    private String courseName;

    @NotBlank(message = "Department name can't be blank.")
    private String departmentName;

    @NotBlank(message = "Subject name can't be blank.")
    private String subjectName;

    private LocalDateTime uploadDateTime;

    private String sharedBy;

    private String imageUrl;

    //file handling
    private String fileName;

    private String fileType;

}
