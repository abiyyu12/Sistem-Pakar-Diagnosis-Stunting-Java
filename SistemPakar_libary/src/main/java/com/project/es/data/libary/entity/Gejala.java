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
@Table(name = "gejala",uniqueConstraints = @UniqueConstraint(columnNames = {"nama_gejala"}))
public class Gejala extends AuditableEntity<String>{

    @Column(name = "nama_gejala",unique = true,length = 100)
    private String namaGejala;

    @Column(name = "bobot")
    private Double bobot;

    @ManyToMany(mappedBy = "gejala")
    private List<Penyakit> penyakit;
}
