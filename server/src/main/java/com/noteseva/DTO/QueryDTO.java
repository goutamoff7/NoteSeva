package com.noteseva.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class QueryDTO {

    @NotBlank(message = "Please enter your First Name")
    private String firstName;

    @NotBlank(message = "Please enter your Last Name")
    private String lastName;

    @NotBlank(message = "Please enter your email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
            , message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Please enter your Phone Number")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone Number must be of 10 Digit")
    private String phoneNumber;

    @NotBlank(message = "Please enter your Query")
    private String query;
}
