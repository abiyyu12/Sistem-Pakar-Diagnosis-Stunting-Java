package com.project.es.data.libary.service;

import com.project.es.data.libary.dto.PenyakitDto;
import com.project.es.data.libary.entity.Penyakit;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PenyakitService {
    List<Penyakit> getPenyakit();
    Penyakit savePenyakit(PenyakitDto penyakitDto,MultipartFile pictures);
    Penyakit findPenyakit(String penyakitName);

    void deletePenyakit(String idPenyakit);

    PenyakitDto findPenyakitDto(String idPenyakit);
    Penyakit updatePenyakit(PenyakitDto penyakitDto, MultipartFile pictures);

    Long penyakitCount();
}
