package com.noteseva.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Organizer
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="organizer_id")
    private Integer id;

    @Column(name="upload_date_time",nullable = false)
    private LocalDateTime uploadDateTime;

    @Column(name="published_year",nullable = false,length = 4)
    private String year;

    //file handling
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Lob
    @Column(name = "file_data", columnDefinition = "longblob", nullable = false, unique = true)
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
}
