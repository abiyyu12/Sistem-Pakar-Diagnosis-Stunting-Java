package com.project.es.data.libary.repository;


import com.project.es.data.libary.entity.DetailDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailDiagnosisRepository extends JpaRepository<DetailDiagnosis,Integer> {
}
