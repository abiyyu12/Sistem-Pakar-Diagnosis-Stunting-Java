package com.project.es.data.libary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisChart {

    private String namaPenyakit;

    private Long totalPenyakit;
}
