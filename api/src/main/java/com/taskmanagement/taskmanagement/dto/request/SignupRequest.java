package com.taskmanagement.taskmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 72, message = "Password must be 8-20 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$",
            message = "Password must have 1 uppercase, 1 number, 1 special character"
    )
    private String password;

    @NotBlank(message = "Full name is required")
    @Size(min = 2,max = 50,message = "Full name must be 2-50 character")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Full name must contain only letters")
    private String fullName;
}