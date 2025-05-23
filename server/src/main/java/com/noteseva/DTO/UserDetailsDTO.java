package com.noteseva.DTO;

import com.noteseva.constants.Role;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserDetailsDTO {

    private Integer id;

    private String username;

    private Role role;

    private String name;

    private String email;

    private String password;

    private LocalDateTime registeredAt;

    private LocalDateTime lastLoginAt;

    private String gender;

    private String collegeName;

    private String imageUrl;

    private String linkedInUrl;

    private String gitHubUrl;

    private String otherUrl;
}
