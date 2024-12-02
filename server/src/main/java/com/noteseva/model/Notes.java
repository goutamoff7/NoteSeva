package com.noteseva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Topic name can't be blank.")
    @Column(name = "topic_name", nullable = false)
    @Size(min = 5, max = 50, message = "Topic name must be within 5 to 50 characters.")
    private String topicName;

    @Column(name = "shared_by", nullable = false)
    private String sharedBy;

    @Column(nullable = false)
    private LocalDate date;

    @NotBlank(message = "Please choose any Subject.")
    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @NotBlank(message = "Please choose any Department.")
    @Column(nullable = false)
    private String department;

    @NotBlank(message = "Please choose any Course.")
    @Column(name = "course_name", nullable = false)
    private String courseName;

    //file handling
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Lob
    @Column(name = "file_data", columnDefinition = "longblob", nullable = false, unique = true)
    private byte[] fileData;

    //verification
    @Column(name = "verification_status", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private VerificationStatus verificationStatus;
}
