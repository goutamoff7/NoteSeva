package com.noteseva.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="notes_id")
    private Integer id;

    @Column(name="upload_date",nullable = false)
    private LocalDate date;

    @Column(name = "topic_name", nullable = false)
    private String topicName;

    //file handling
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Lob
    @Column(name = "file_data", columnDefinition = "longblob", nullable = false)
    private byte[] fileData;

    @Column(name = "file_data_hash", nullable = false, unique = true)
    private String fileDataHash;

    //relationship
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="user_id" ,nullable = false)
    private Users user;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="subject_Assignment_id",nullable = false)
    private SubjectAssignment subjectAssignment;
}
