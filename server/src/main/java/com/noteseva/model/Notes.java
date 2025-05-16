package com.noteseva.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="notes_id")
    private Integer id;

    @Column(name="document_type",nullable = false)
    private String documentType;

    @Column(name="upload_date_time",nullable = false)
    private LocalDateTime uploadDateTime;

    @Column(name = "topic_name", nullable = false)
    private String topicName;

    //file handling
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @JsonIgnore
    @Lob
    @Column(name = "file_data", columnDefinition = "longblob", nullable = false)
    private byte[] fileData;

    //relationship
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="user_id" ,nullable = false)
    private Users user;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="subject_Assignment_id",nullable = false)
    private SubjectAssignment subjectAssignment;

    // Bookmark

    @JsonIgnore
    @ManyToMany(mappedBy = "bookmarkedNotes", fetch = FetchType.LAZY)
    private Set<Users> bookmarkedByUsers = new HashSet<>();

    // Like

    @JsonIgnore
    @ManyToMany(mappedBy = "likedNotes", fetch = FetchType.LAZY)
    private Set<Users> likedByUsers = new HashSet<>();

}
