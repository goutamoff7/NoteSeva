package com.noteseva.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UploadedDocsDTO {

    private Set<NotesDTO> notesDTOList;

    private Set<OrganizerDTO> organizerDTOList;

    private Set<PYQDTO> pyqDTOList;
}
