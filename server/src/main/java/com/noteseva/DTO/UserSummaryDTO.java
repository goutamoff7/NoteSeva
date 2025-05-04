package com.noteseva.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserSummaryDTO {

    private String name;
    private String username;
    private String gender;
    private String collegeName;
    private String imageUrl;
    private LocalDateTime registeredAt;
    private LocalDateTime lastLoginAt;
}
