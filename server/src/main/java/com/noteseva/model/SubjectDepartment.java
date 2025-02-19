package com.noteseva.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="subject_department")
public class SubjectDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_department_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="subject_id" ,nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="department_id" ,nullable = false)
    private Department department;

    @OneToMany(mappedBy = "subjectDepartment",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Set<Notes> notes;

    @OneToMany(mappedBy = "subjectDepartment",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Set<Organizer> organizer;

    @OneToMany(mappedBy = "subjectDepartment",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Set<PYQ> pyq;
}
