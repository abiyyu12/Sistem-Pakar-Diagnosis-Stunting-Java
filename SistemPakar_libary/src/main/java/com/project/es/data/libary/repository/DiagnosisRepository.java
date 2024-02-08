package com.project.es.data.libary.repository;

import com.project.es.data.libary.dto.DiagnosisChart;
import com.project.es.data.libary.entity.Diagnosis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis,String> {

    @Query("SELECT d FROM Diagnosis d INNER JOIN Pasiens p ON d.pasiens.id = p.id" +
            " WHERE p.id =:idPasien" +" ORDER BY d.createdAt DESC")
    Page<Diagnosis> getAllDiagnosisByPasiensId(@Param("idPasien") String idPasien, Pageable pageable);

    @Query("SELECT d FROM Diagnosis d INNER JOIN Pasiens p ON d.pasiens.id = p.id" +
            " WHERE d.id =:idDiagnosis AND p.id =:idPasien")
    Diagnosis getDiagnosisByIdPasiens(@Param("idDiagnosis") String idDiagnosis,@Param("idPasien") String idPasien);

    @Query("SELECT NEW com.project.es.data.libary.dto.DiagnosisChart(p.namaPenyakit, COUNT(d.id)) " +
            "FROM Diagnosis d " +
            "JOIN d.detailDiagnoses dd " +
            "JOIN dd.penyakit p " +
            "WHERE dd.hasilDiagnosis = (SELECT MAX(dd2.hasilDiagnosis) FROM DetailDiagnosis dd2 WHERE dd2.diagnosis = d) " +
            "AND YEAR(d.createdAt) = YEAR(CURRENT_DATE) " +
            "GROUP BY p.namaPenyakit")
    List<DiagnosisChart> countDiagnosedPenyakit();
}
