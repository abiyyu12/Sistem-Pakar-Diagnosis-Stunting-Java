package com.project.es.data.libary.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "penyakit",uniqueConstraints = @UniqueConstraint(columnNames = {"nama_penyakit"}))
public class Penyakit extends AuditableEntity<String>{

    @Column(name = "nama_penyakit",length = 100)
    private String namaPenyakit;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String pictures;

    @Column(columnDefinition = "TEXT")
    private String keterangan;

    @Column(columnDefinition = "TEXT")
    private String solusi;

    @Column(name = "total_bobot")
    private Double totalBobot;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "gejala_penyakit",
            joinColumns = @JoinColumn(name = "penyakit_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "gejala_id",referencedColumnName = "id")
    )
    private List<Gejala> gejala;

    @OneToMany(mappedBy = "penyakit",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<DetailDiagnosis> detailDiagnoses;

}