package com.noteseva.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerDTO {

    @NotBlank(message = "Please choose any Year")
    private String year;

    //subject selection
    @NotBlank(message = "Course name can't be blank.")
    private String course;

    @NotBlank(message = "Department can't be blank.")
    private String department;

    @NotBlank(message = "Subject can't be blank.")
    private String subject;

    //file handling
    private String fileName;

    private String fileType;

    private byte[] fileData;

}
