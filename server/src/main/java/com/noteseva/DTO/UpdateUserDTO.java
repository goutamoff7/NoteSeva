package com.noteseva.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
public class UpdateUserDTO {

    private String collegeName;
    private String gender;
    private String linkedInUrl;
    private String gitHubUrl;
    private String othersUrl;
}
