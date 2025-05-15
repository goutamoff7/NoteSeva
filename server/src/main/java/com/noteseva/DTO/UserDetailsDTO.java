package com.noteseva.DTO;

import com.noteseva.constants.Role;
import com.noteseva.model.Notes;
import com.noteseva.model.Organizer;
import com.noteseva.model.PYQ;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

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

    private String refreshToken;

    private Set<Notes> notes;

    private Set<Organizer> organizer;

    private Set<PYQ> pyq;

    private Set<Notes> bookmarkedNotes;
    private Set<Organizer> bookmarkedOrganizer;
    private Set<PYQ> bookmarkedPyq;
}
