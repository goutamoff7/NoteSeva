package com.noteseva.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
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

    @NotBlank(message = "Please enter your name")
    @Column(name="name",nullable = false)
    private String name;

    @NotBlank(message = "Please enter a valid email")
    @Column(name = "email", nullable = false , unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
            , message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Please enter a valid password")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$"
    , message = "At least one lowercase letter [a-z]\n" +
            "    At least one uppercase letter [A-Z]\n" +
            "    At least one special character [@#$%^&+=]\n" +
            "    Minimum length of 8 characters.\n" +
            "    At least one digit [0-9]")
    @Column(name="password",nullable = false)
    private String password;

}
