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
@Table(name = "diagnosis")
public class Diagnosis extends AuditableEntity<String> {

    @ManyToOne
    @JoinColumn(name = "id_pasien",referencedColumnName = "id")
    private Pasiens pasiens;

    @OneToMany(mappedBy = "diagnosis",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<DetailDiagnosis> detailDiagnoses;
}
