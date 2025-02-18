package com.noteseva.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Username can't be null")
    @Column(name="shared_by", nullable = false)
    private String sharedBy;

    @Column(name="published_date",nullable = false)
    private LocalDate date;

    @NotBlank(message = "Please choose any Year")
    @Column(name="published_year",nullable = false,length = 4)
    private String year;

    @ManyToOne
    @NotNull(message ="Choose proper subject name")
    @JoinColumn(name="subject_department_id",nullable = false)
    private SubjectDepartment subjectDepartment;

    //file handling
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Lob
    @Column(name = "file_data", columnDefinition = "longblob", nullable = false, unique = true)
    private byte[] fileData;
}
