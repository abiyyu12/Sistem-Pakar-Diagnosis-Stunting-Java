package com.project.es.data.libary.service.impl;

import com.project.es.data.libary.Util.GeneratedId;
import com.project.es.data.libary.dto.GejalaDto;
import com.project.es.data.libary.entity.Admin;
import com.project.es.data.libary.entity.Gejala;
import com.project.es.data.libary.repository.GejalaRepository;
import com.project.es.data.libary.service.GejalaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GejalaServiceImpl implements GejalaService {

    @Autowired
    public GejalaRepository gejalaRepository;

    @Override
    public List<Gejala> listGejala() {
        return gejalaRepository.findAll();
    }

    @Override
    public Gejala addGejala(GejalaDto gejalaDto) {
        Gejala gejala = new Gejala();
        gejala.setId(generatedId());
        gejala.setNamaGejala(gejalaDto.getNamaGejala());
        gejala.setCreatedAt(LocalDateTime.now());
        gejala.setUpdatedAt(LocalDateTime.now());
        gejala.setBobot(gejalaDto.getBobotGejala());
        return gejalaRepository.save(gejala);
    }

    @Override
    public Gejala findByNamaGejala(String namaGejala) {
        return gejalaRepository.findByNamaGejala(namaGejala);
    }

    @Override
    @Transactional
    public Gejala updateGejala(GejalaDto gejalaDto) {
        Gejala gejala = gejalaRepository.findLockingById(gejalaDto.getIdGejala()).get();
        gejala.setId(gejalaDto.getIdGejala());
        gejala.setNamaGejala(gejalaDto.getNamaGejala());
        gejala.setBobot(gejalaDto.getBobotGejala());
        gejala.setUpdatedAt(LocalDateTime.now());
        return gejalaRepository.save(gejala);
    }

    @Override
    public GejalaDto findGejalaById(String idGejala) {
        return toGejalaDto(gejalaRepository.findById(idGejala).get());
    }

    @Override
    public Gejala searchGejalaById(String idGejala) {
        return gejalaRepository.findById(idGejala).get();
    }

    @Override
    public void deleteGejala(String idGejala) {
        gejalaRepository.deleteById(idGejala);
    }

    @Override
    public Long gejalaCount() {
        return gejalaRepository.count();
    }

    private String generatedId(){
        List<Gejala> all = gejalaRepository.findAll();
        if(all.isEmpty()){
            return "GJ001";
        }else {
            Gejala last = all.getLast();
            return GeneratedId.generatedIdGejala(last.getId());
        }
    }

    private GejalaDto toGejalaDto(Gejala gejala){
        GejalaDto gejalaDto = new GejalaDto();
        gejalaDto.setIdGejala(gejala.getId());
        gejalaDto.setNamaGejala(gejala.getNamaGejala());
        gejalaDto.setBobotGejala(gejala.getBobot());
        return gejalaDto;
    }
}
