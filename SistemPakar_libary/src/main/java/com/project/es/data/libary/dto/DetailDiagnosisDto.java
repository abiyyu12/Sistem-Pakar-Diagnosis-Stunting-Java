package com.project.es.data.libary.dto;

import com.project.es.data.libary.entity.Penyakit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailDiagnosisDto {

    private Penyakit penyakit;

    private Double hasilDiagnosis;
}
