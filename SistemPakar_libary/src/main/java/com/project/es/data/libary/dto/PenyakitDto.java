package com.project.es.data.libary.dto;

import com.project.es.data.libary.entity.Gejala;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PenyakitDto {
    private String idPenyakit;

    private String gambarPenyakit;


    @Size(min = 1,max = 100,message = "Nama Penyakit Hanya bisa berisi 1-100 characters")
    @Pattern(regexp = "^[^0-9]+$", message = "Penyakit Harus Berupa Huruf")
    private String namaPenyakit;

    @NotBlank(message = "Keterangan Wajib Diisi")
    private String keterangan;

    @NotBlank(message = "Solusi Wajib Diisi")
    private String solusi;

    private Double total_bobot;

    private List<Gejala> gejalaList;
}
