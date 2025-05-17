package com.noteseva.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class PYQDTO {

    private Integer id;

    private String documentType;

    @NotBlank(message = "Please choose any Year")
    private String year;

    //subject selection
    @NotBlank(message = "Course name can't be blank.")
    private String courseName;

    @NotBlank(message = "Department name can't be blank.")
    private String departmentName;

    @NotBlank(message = "Subject name can't be blank.")
    private String subjectName;

    private LocalDateTime uploadDateTime;

    private String sharedBy;

    private String imageUrl;

    private Integer likeCount;

    private boolean currentUserLiked;

    private Set<UserPreviewDTO> likedUserList;

    private boolean currentUserBookmarked;


    //file handling
    private String fileName;

    private String fileType;
}
