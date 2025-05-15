package com.noteseva.DTO;

import com.noteseva.validation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDTO {

    @NotBlank(message = "Please enter a valid email",
            groups = {GenerateOTPValidation.class,
                    VerifyOTPValidation.class,
                    ForgotPasswordValidation.class})
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
            , message = "Please enter a valid email",
            groups = {GenerateOTPValidation.class,
                    VerifyOTPValidation.class,
                    ForgotPasswordValidation.class})
    private String email;

    @NotBlank(message = "Please enter a valid OTP",
            groups = {VerifyOTPValidation.class})
    @Pattern(regexp = "^\\d{6}$", message = "OTP must be a 6-digit number",
            groups = {VerifyOTPValidation.class})
    private String otp;

    @NotBlank(message = "Please enter a valid password",
            groups = {ChangePasswordValidation.class})
    private String oldPassword;

    @NotBlank(message = "Please enter a valid password",
            groups = {ForgotPasswordValidation.class,ChangePasswordValidation.class})
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$"
            , message = "At least one lowercase letter [a-z]\n" +
            "    At least one uppercase letter [A-Z]\n" +
            "    At least one special character [@#$%^&+=]\n" +
            "    Minimum length of 8 characters.\n" +
            "    At least one digit [0-9]",
            groups = {ForgotPasswordValidation.class,ChangePasswordValidation.class})
    private String newPassword;

    @NotBlank(message = "Please enter a valid password",
            groups = {ForgotPasswordValidation.class,ChangePasswordValidation.class})
    private String confirmPassword;
}
