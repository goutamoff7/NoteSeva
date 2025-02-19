package com.noteseva.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PYQ {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="pyq_id")
    private Integer id;

    @Column(name="published_date",nullable = false)
    private LocalDate date;

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
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="user_id" ,nullable = false)
    private Users user;

    @ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="subject_department_id",nullable = false)
    private SubjectDepartment subjectDepartment;
}
