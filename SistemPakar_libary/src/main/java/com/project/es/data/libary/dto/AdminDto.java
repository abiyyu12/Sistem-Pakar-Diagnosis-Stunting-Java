package com.project.es.data.libary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

    @NotNull
    @Size(min = 5, max = 50, message = "Email contains 10-50 characters")
    private String email;

    @NotNull
    @Size(min = 5, max = 255, message = "Password name contains 5-50 characters")
    private String password;

    @NotNull
    @Size(min = 1,max = 30,message = "First Name Must Contains 1-30 Characters")
    @Pattern(regexp = "^[^0-9]+$", message = "First Name Must Not Contain Numbers")
    private String firstName;

    @NotNull
    @Size(min = 1,max = 30,message = "Last Name Must Contains 1-30 Characters")
    @Pattern(regexp = "^[^0-9]+$", message = "Last Must Not Contain Numbers")
    private String lastName;

    @NotNull
    @Size(min = 1,max = 100,message = "Full Name Must Contains 1-100 Characters")
    @Pattern(regexp = "^[^0-9]+$", message = "Full Name Must Not Contain Numbers")
    private String fullName;

    private String confirmPassword;
}
