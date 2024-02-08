package com.project.es.data.libary.service;

import com.project.es.data.libary.dto.PasienDto;
import com.project.es.data.libary.dto.PasienPasswordChangeDto;
import com.project.es.data.libary.entity.Pasiens;

public interface PasienService {

    Pasiens findByEmail(String email);

    Pasiens savePasiens(PasienDto pasienDto);

    PasienDto findPasienByEmailDto(String email);

    Pasiens updatePasiens(PasienDto pasienDto);

    Long pasienCount();

    boolean resetPasswordPasien(PasienPasswordChangeDto pasienPasswordChangeDto);
}
