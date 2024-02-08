package com.project.es.data.libary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detail_diagnosis")
public class DetailDiagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_diagnosis",referencedColumnName = "id")
    private Diagnosis diagnosis;

    @ManyToOne
    @JoinColumn(name = "id_penyakit",referencedColumnName = "id")
    private Penyakit penyakit;

    @Column(name = "hasil_diagnosis")
    private Double hasilDiagnosis;
}
