package com.noteseva.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer id;

    @Column(name="username",nullable = false , unique = true)
    private String username;

    @Enumerated(value = EnumType.STRING)
    @Column(name="user_role")
    private Role role;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name = "email", nullable = false , unique = true)
    private String email;

    @JsonIgnore
    @Column(name="password",nullable = false)
    private String password;

    @Column(name="registered_at")
    private LocalDateTime registeredAt;

    @Column(name="last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name="gender")
    private String gender;

    @Column(name="college_name")
    private String collegeName;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="LinkedIn_url")
    private String linkedInUrl;

    @Column(name="GitHub_url")
    private String gitHubUrl;

    @Column(name="others_url")
    private String otherUrl;

    @JsonIgnore
    @Column(name="refresh_token")
    private String refreshToken;

    //relationship
    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Set<Notes> notes;

    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Set<Organizer> organizer;

    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Set<PYQ> pyq;

}
