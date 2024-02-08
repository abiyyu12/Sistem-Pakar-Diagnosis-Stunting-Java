package com.project.es.data.libary.service;

import com.project.es.data.libary.dto.DetailDiagnosisDto;
import com.project.es.data.libary.dto.DiagnosisChart;
import com.project.es.data.libary.dto.PenyakitDto;
import com.project.es.data.libary.entity.DetailDiagnosis;
import com.project.es.data.libary.entity.Diagnosis;
import com.project.es.data.libary.entity.Penyakit;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiagnosisService {

    Diagnosis findDiagnosisById(String idDiagnosis);
    void saveDetailDiagnosis(DetailDiagnosis detailDiagnosis);
    Diagnosis saveDiagnosis(String email);
    DetailDiagnosisDto detailDiagnosis(Penyakit penyakit, List<String> gejalaChecked);

    Page<Diagnosis> getAllDiagnosisByIdPasiens(int pageNo,String pasienId);

    Diagnosis getDiagnosisByIdPasienAndIdDiagnosis(String idDiagnosis,String idPasien,List<String> penyakit,List<Double> bobot);
    Penyakit bobotTerbersar(Diagnosis diagnosis);

    Double bobotValue(Diagnosis diagnosis);

    Long countDiagnosis();

    List<DiagnosisChart> getChartDiagnosis();

}
