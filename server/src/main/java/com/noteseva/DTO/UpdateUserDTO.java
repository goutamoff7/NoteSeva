package com.noteseva.DTO;

import lombok.Data;

@Data
public class UpdateUserDTO {

    private String collegeName;
    private String gender;
    private String linkedInUrl;
    private String gitHubUrl;
    private String othersUrl;
}
