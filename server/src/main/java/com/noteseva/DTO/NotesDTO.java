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

    @NotBlank(message = "Topic name can't be blank.")
    @Column(name = "topic_name", nullable = false)
    @Size(min = 5, max = 50, message = "Topic name must be within 5 to 50 characters.")
    private String topicName;

    @ManyToOne
    @NotNull(message ="Choose proper subject name")
    @JoinColumn(name="subject_department_id",nullable = false)
    private SubjectDepartment subjectDepartment;

    //file handling
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Lob
    @Column(name = "file_data", columnDefinition = "longblob", nullable = false, unique = true)
    private byte[] fileData;
}
