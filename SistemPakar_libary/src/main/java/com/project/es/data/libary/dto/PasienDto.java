package com.project.es.data.libary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasienDto {

    @NotNull
    @Size(min = 5, max = 100, message = "Email contains 5-100 characters")
    private String email;

    @NotNull
    @Size(min = 5, max = 255, message = "Password name contains 5-255 characters")
    private String password;

    @Size(min = 1,max = 50,message = "FirstName must contains 1-50 characters")
    @Pattern(regexp = "^[^0-9]+$", message = "First Name Must Not Contain Numbers")
    private String firstName;

    @Size(min = 1,max = 50,message = "FirstName must contains 1-50 characters")
    @Pattern(regexp = "^[^0-9]+$", message = "Last Name Must Not Contain Numbers")
    private String lastName;

    @Size(min = 5,max = 100,message = "FirstName must contains 5-100 characters")
    @Pattern(regexp = "^[^0-9]+$", message = "Full Name Must Not Contain Numbers")
    private String fullName;

    @Size(min = 1,message = "Phone Number Cannot Blank")
    private String phone;

    @NotBlank(message = "Address Cannot Blank")
    private String address;
}
