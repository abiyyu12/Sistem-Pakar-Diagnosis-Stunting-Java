package com.project.es.data.libary.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasienPasswordChangeDto {


    @NotNull
    @Size(min = 5, max = 50, message = "Email contains 10-50 characters")
    private String email;


    @NotNull
    @Size(min = 5, max = 255, message = "Old Password name contains 5-50 characters")
    private String oldPassword;


    @NotNull
    @Size(min = 5, max = 255, message = "New Password name contains 5-50 characters")
    private String newPassword;
}
