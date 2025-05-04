package com.noteseva.model;

import com.noteseva.constants.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="contact_us")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="first_name",nullable = false)
    private String firstName;

    @Column(name="last_name",nullable = false)
    private String lastName;

    @Column(name="user_email",nullable = false)
    private String email;

    @Column(name="phone_number",nullable = false)
    private String phoneNumber;

    @Column(name="user_query",nullable = false)
    private String query;

    @Enumerated(value = EnumType.STRING)
    @Column(name="query_status",nullable = false)
    private Status status;

    @Column(name="admin_remarks",nullable = false)
    private String remarks;

    @Column(name="query_raised_at",nullable = false)
    private LocalDateTime raisedAt;

    @Column(name="query_resolved_at")
    private LocalDateTime resolvedAt;

}
