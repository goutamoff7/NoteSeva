package com.noteseva.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotesDTO {

    private Integer id;

    //subject selection
    @NotBlank(message = "Course name can't be blank.")
    private String courseName;

    @NotBlank(message = "Department name can't be blank.")
    private String departmentName;

    @NotBlank(message = "Subject name can't be blank.")
    private String subjectName;

    @NotBlank(message = "Topic name can't be blank.")
    @Size(min = 5, max = 50, message = "Topic must be within 5 to 50 characters.")
    private String topicName;

    //file handling
    private String fileName;

    private String fileType;
}
