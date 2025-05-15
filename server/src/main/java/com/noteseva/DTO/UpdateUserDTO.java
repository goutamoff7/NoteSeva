package com.noteseva.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {

    private String collegeName;
    private String gender;
    private String linkedInUrl;
    private String gitHubUrl;
    private String othersUrl;
}
