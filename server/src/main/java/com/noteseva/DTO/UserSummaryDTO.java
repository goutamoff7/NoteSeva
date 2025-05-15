package com.noteseva.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserSummaryDTO {

    private String name;
    private String username;
    private String gender;
    private String collegeName;
    private String imageUrl;
    private LocalDateTime registeredAt;
    private LocalDateTime lastLoginAt;
}
