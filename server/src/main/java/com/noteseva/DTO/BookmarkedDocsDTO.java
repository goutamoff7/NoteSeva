package com.noteseva.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookmarkedDocsDTO {

    private Set<NotesDTO> bookmarkedNotesDTOList;

    private Set<OrganizerDTO> bookmarkedOrganizerDTOList;

    private Set<PYQDTO> bookmarkedPyqDTOList;
}
