package com.project.es.data.libary.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GejalaDto {

    private String idGejala;

    @Size(min = 1,max = 100,message = "Nama Gejala Harus Berisi Character 1-100 Character")
    @Pattern(regexp = "^[^0-9]+$", message = "Nama Gejala Harus Berupa Huruf")
    private String namaGejala;

    @NotNull(message = "Bobot Gejala Wajib Di isi")
    private Double bobotGejala;
}
