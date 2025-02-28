package com.noteseva.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="subject_Assignment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubjectAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_Assignment_id")
    private Integer id;

    //relationship
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="subject_id" ,nullable = false)
    private Subject subject;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="department_id" ,nullable = false)
    private Department department;

    @JsonIgnore
    @OneToMany(mappedBy = "subjectAssignment",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Set<Notes> notes;

    @JsonIgnore
    @OneToMany(mappedBy = "subjectAssignment",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Set<Organizer> organizer;

    @JsonIgnore
    @OneToMany(mappedBy = "subjectAssignment",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Set<PYQ> pyq;
}
