package com.noteseva.DTO;
import com.noteseva.model.SubjectDepartment;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    //subject selection
    @NotBlank(message = "Course can't be blank.")
    private String course;

    @NotBlank(message = "Department can't be blank.")
    private String department;

    @NotBlank(message = "Subject can't be blank.")
    private String subject;

    @NotBlank(message = "Topic can't be blank.")
    @Size(min = 5, max = 50, message = "Topic must be within 5 to 50 characters.")
    private String topic;

    //file handling
    private String fileName;

    private String fileType;

    private byte[] fileData;
}
