package com.noteseva.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookmarkedDocsDTO {

    private Set<NotesDTO> bookmarkedNotesDTO;

    private Set<OrganizerDTO> bookmarkedOrganizerDTO;

    private Set<PYQDTO> bookmarkedPyqDTO;
}
