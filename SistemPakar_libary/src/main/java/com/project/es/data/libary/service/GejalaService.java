package com.project.es.data.libary.service;

import com.project.es.data.libary.dto.GejalaDto;
import com.project.es.data.libary.entity.Gejala;

import java.util.List;

public interface GejalaService {
    List<Gejala> listGejala();
    Gejala addGejala(GejalaDto gejalaDto);
    Gejala findByNamaGejala(String namaGejala);

    Gejala updateGejala(GejalaDto gejalaDto);

    GejalaDto findGejalaById(String idGejala);
    Gejala searchGejalaById(String idGejala);

    void deleteGejala(String idGejala);

    Long gejalaCount();
}
