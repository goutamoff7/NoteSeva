package com.noteseva.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false , unique = true)
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$"
    , message = "At least one lowercase letter [a-z]\n" +
            "    At least one uppercase letter [A-Z]\n" +
            "    At least one special character [@#$%^&+=]\n" +
            "    Minimum length of 8 characters.\n" +
            "    At least one digit [0-9]")
    private String password;

    @NotBlank
    @Column(nullable = false , unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    , message = "Please enter a valid email")
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;


}
