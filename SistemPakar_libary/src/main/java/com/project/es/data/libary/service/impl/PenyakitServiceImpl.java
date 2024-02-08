package com.project.es.data.libary.service.impl;

import com.project.es.data.libary.Util.GeneratedId;
import com.project.es.data.libary.dto.PenyakitDto;
import com.project.es.data.libary.entity.Gejala;
import com.project.es.data.libary.entity.Penyakit;
import com.project.es.data.libary.repository.PenyakitRepository;
import com.project.es.data.libary.service.PenyakitService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
public class PenyakitServiceImpl implements PenyakitService {

    @Autowired
    private PenyakitRepository penyakitRepository;

    @Override
    public List<Penyakit> getPenyakit() {
        return penyakitRepository.findAll();
    }

    @Override
    public Penyakit savePenyakit(PenyakitDto penyakitDto,MultipartFile pictures) {
        try {
            Penyakit penyakit = new Penyakit();
            penyakit.setNamaPenyakit(penyakitDto.getNamaPenyakit());
            penyakit.setPictures(Base64.getEncoder().encodeToString(pictures.getBytes()));
            penyakit.setId(generatedId());
            penyakit.setKeterangan(penyakitDto.getKeterangan());
            penyakit.setSolusi(penyakitDto.getSolusi());
            penyakit.setTotalBobot(penyakitDto.getTotal_bobot());
            penyakit.setCreatedAt(LocalDateTime.now());
            penyakit.setUpdatedAt(LocalDateTime.now());
            penyakit.setGejala(penyakitDto.getGejalaList());
            return penyakitRepository.save(penyakit);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Penyakit findPenyakit(String penyakitName) {
        return penyakitRepository.findByNamaPenyakit(penyakitName);
    }



    @Override
    public void deletePenyakit(String idPenyakit) {
        penyakitRepository.deleteById(idPenyakit);
    }

    @Override
    public PenyakitDto findPenyakitDto(String idPenyakit) {
        return toPenyakitDto(penyakitRepository.findById(idPenyakit).get());
    }


    @Override
    @Transactional
    public Penyakit updatePenyakit(PenyakitDto penyakitDto, MultipartFile pictures) {
        try {
            Penyakit penyakit = penyakitRepository.findLockingById(penyakitDto.getIdPenyakit()).get();
            if(pictures.isEmpty()){
                penyakit.setPictures(penyakit.getPictures());
            }else {
                penyakit.setPictures(Base64.getEncoder().encodeToString(pictures.getBytes()));
            }
            penyakit.setId(penyakitDto.getIdPenyakit());
            penyakit.setNamaPenyakit(penyakitDto.getNamaPenyakit());
            penyakit.setSolusi(penyakitDto.getSolusi());
            penyakit.setTotalBobot(penyakitDto.getTotal_bobot());
            penyakit.setKeterangan(penyakitDto.getKeterangan());
            penyakit.setUpdatedAt(LocalDateTime.now());
            penyakit.setGejala(penyakitDto.getGejalaList());
            return penyakitRepository.save(penyakit);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long penyakitCount() {
        return penyakitRepository.count();
    }

    private String generatedId(){
        List<Penyakit> all = penyakitRepository.findAll();
        if(all.isEmpty()){
            return "PK001";
        }else {
            Penyakit last = all.getLast();
            return GeneratedId.generatedIdPenyakit(last.getId());
        }
    }

    private PenyakitDto toPenyakitDto(Penyakit penyakit){
        PenyakitDto penyakitDto = new PenyakitDto();
        penyakitDto.setIdPenyakit(penyakit.getId());
        penyakitDto.setNamaPenyakit(penyakit.getNamaPenyakit());
        penyakitDto.setGambarPenyakit(penyakit.getPictures());
        penyakitDto.setKeterangan(penyakit.getKeterangan());
        penyakitDto.setSolusi(penyakit.getSolusi());
        penyakitDto.setTotal_bobot(penyakit.getTotalBobot());
        penyakitDto.setGejalaList(penyakit.getGejala());
        return penyakitDto;
    }
}
