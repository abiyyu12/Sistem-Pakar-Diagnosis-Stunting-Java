package com.project.es.data.libary.service.impl;

import com.project.es.data.libary.Util.RandomStringsUtils;
import com.project.es.data.libary.dto.DetailDiagnosisDto;
import com.project.es.data.libary.dto.DiagnosisChart;
import com.project.es.data.libary.entity.DetailDiagnosis;
import com.project.es.data.libary.entity.Diagnosis;
import com.project.es.data.libary.entity.Gejala;
import com.project.es.data.libary.entity.Penyakit;
import com.project.es.data.libary.repository.DetailDiagnosisRepository;
import com.project.es.data.libary.repository.DiagnosisRepository;
import com.project.es.data.libary.repository.PasiensRepository;
import com.project.es.data.libary.repository.PenyakitRepository;
import com.project.es.data.libary.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    @Autowired
    private PasiensRepository pasiensRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private DetailDiagnosisRepository detailDiagnosisRepository;

    @Autowired
    private PenyakitRepository penyakitRepository;

    @Override
    public Diagnosis findDiagnosisById(String idDiagnosis) {
        return diagnosisRepository.findById(idDiagnosis).get();
    }

    @Override
    public Diagnosis saveDiagnosis(String email) {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(RandomStringsUtils.randomStrings());
        diagnosis.setCreatedAt(LocalDateTime.now());
        diagnosis.setUpdatedAt(LocalDateTime.now());
        diagnosis.setPasiens(pasiensRepository.findByEmail(email));
        diagnosis.setPasiens(pasiensRepository.findByEmail(email));
        Diagnosis save = diagnosisRepository.save(diagnosis);
        return save;
    }


    @Override
    public List<DiagnosisChart> getChartDiagnosis(){
        List<DiagnosisChart> diagnosisCharts = diagnosisRepository.countDiagnosedPenyakit();
        System.out.println(diagnosisCharts);
        return diagnosisCharts;
    }


    public void saveDetailDiagnosis(DetailDiagnosis detailDiagnosis){
        detailDiagnosisRepository.save(detailDiagnosis);
    }


    public DetailDiagnosisDto detailDiagnosis(Penyakit penyakit, List<String> gejalaChecked){
        DetailDiagnosisDto detailDiagnosis = new DetailDiagnosisDto();
        Double hasilPrediksi = 0.0;
        for (String s : gejalaChecked) {
            for (Gejala gejala : penyakit.getGejala()) {
                if(s.equals(gejala.getId())){
                    Double tempPrediksi = 1*gejala.getBobot();
                    hasilPrediksi+=tempPrediksi;
                }
                else {
                    hasilPrediksi+=0;
                }
            }
        }
        Double hasilAkhir = hasilPrediksi / penyakit.getTotalBobot();
        Double percentageResult = hasilAkhir * 100;
        double rounded = Math.round(percentageResult);
        detailDiagnosis.setHasilDiagnosis(rounded);
        detailDiagnosis.setPenyakit(penyakit);
        return detailDiagnosis;
    }

    @Override
    public Page<Diagnosis> getAllDiagnosisByIdPasiens(int pageNo,String pasienId) {
        Pageable pageable = PageRequest.of(pageNo,12);
        Page<Diagnosis> diagnosisByPasiensId = diagnosisRepository.getAllDiagnosisByPasiensId(pasienId, pageable);
        return diagnosisByPasiensId;
    }

    @Override
    public Diagnosis getDiagnosisByIdPasienAndIdDiagnosis(String idDiagnosis, String idPasien, List<String> penyakit, List<Double> bobot) {
        Diagnosis diagnosisByIdPasiens = diagnosisRepository.getDiagnosisByIdPasiens(idDiagnosis, idPasien);
        for (DetailDiagnosis detailDiagnosis : diagnosisByIdPasiens.getDetailDiagnoses()) {
            penyakit.add(detailDiagnosis.getPenyakit().getNamaPenyakit());
            bobot.add(detailDiagnosis.getHasilDiagnosis());
        }
        return diagnosisByIdPasiens;
    }

    public Penyakit bobotTerbersar(Diagnosis diagnosis){
        Double bobotTerbesar = 0.0;
        String idPenyakit = null;
        for (DetailDiagnosis detailDiagnosis : diagnosis.getDetailDiagnoses()) {
            Double bobotPenyakit = detailDiagnosis.getHasilDiagnosis();
            if(bobotPenyakit > bobotTerbesar){
                idPenyakit = detailDiagnosis.getPenyakit().getId();
                bobotTerbesar = detailDiagnosis.getHasilDiagnosis();
            }
        }
        return penyakitRepository.findById(idPenyakit).get();
    }

    @Override
    public Double bobotValue(Diagnosis diagnosis) {
        Double bobotTerbesar = 0.0;
        for (DetailDiagnosis detailDiagnosis : diagnosis.getDetailDiagnoses()) {
            Double bobotPenyakit = detailDiagnosis.getHasilDiagnosis();
            if(bobotPenyakit > bobotTerbesar){
                bobotTerbesar = detailDiagnosis.getHasilDiagnosis();
            }
        }
        return bobotTerbesar;
    }

    @Override
    public Long countDiagnosis() {
        return diagnosisRepository.count();
    }


}
