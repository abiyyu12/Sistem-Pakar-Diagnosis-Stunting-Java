package com.project.es.data.libary.service.impl;

import com.project.es.data.libary.Util.GeneratedId;
import com.project.es.data.libary.dto.PasienDto;
import com.project.es.data.libary.dto.PasienPasswordChangeDto;
import com.project.es.data.libary.entity.Admin;
import com.project.es.data.libary.entity.Pasiens;
import com.project.es.data.libary.repository.PasiensRepository;
import com.project.es.data.libary.repository.RoleRepository;
import com.project.es.data.libary.service.PasienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class PasienServiceImpl implements PasienService {

    @Autowired
    private PasiensRepository pasiensRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Pasiens findByEmail(String email) {
        return pasiensRepository.findByEmail(email);
    }

    @Override
    public Pasiens savePasiens(PasienDto pasienDto) {
        Pasiens pasiens = new Pasiens();
        pasiens.setId(generatedId());
        pasiens.setEmail(pasienDto.getEmail());
        pasiens.setFirstName(pasienDto.getFirstName());
        pasiens.setLastName(pasienDto.getLastName());
        pasiens.setFullName(pasienDto.getFullName());
        pasiens.setPassword(pasienDto.getPassword());
        pasiens.setAddress(pasienDto.getAddress());
        pasiens.setPhone(pasienDto.getPhone());
        pasiens.setCreatedAt(LocalDateTime.now());
        pasiens.setUpdatedAt(LocalDateTime.now());
        pasiens.setRoles(Arrays.asList(roleRepository.findByName("PASIENS")));
        return pasiensRepository.save(pasiens);
    }

    @Override
    public PasienDto findPasienByEmailDto(String email) {
        Pasiens pasiens = pasiensRepository.findByEmail(email);
        PasienDto pasienDto = convertPasienToDto(pasiens);
        return pasienDto;
    }

    @Override
    public Pasiens updatePasiens(PasienDto pasienDto) {
        Pasiens pasiens = pasiensRepository.findByEmail(pasienDto.getEmail());
        pasiens.setUpdatedAt(LocalDateTime.now());
        pasiens.setFirstName(pasienDto.getFirstName());
        pasiens.setLastName(pasienDto.getLastName());
        pasiens.setFullName(pasienDto.getFullName());
        pasiens.setPhone(pasienDto.getPhone());
        pasiens.setAddress(pasienDto.getAddress());
        return pasiensRepository.save(pasiens);
    }

    @Override
    public Long pasienCount() {
        return pasiensRepository.count();
    }

    @Override
    public boolean resetPasswordPasien(PasienPasswordChangeDto pasienPasswordChangeDto) {
        boolean isSuccess = false;
        Pasiens byEmail = pasiensRepository.findByEmail(pasienPasswordChangeDto.getEmail());
        if(passwordEncoder.matches(pasienPasswordChangeDto.getOldPassword(),byEmail.getPassword()) ==  true){
            byEmail.setPassword(passwordEncoder.encode(pasienPasswordChangeDto.getNewPassword()));
            pasiensRepository.save(byEmail);
            isSuccess = true;
        }else{
            isSuccess = false;
        }
        return isSuccess;
    }

    private PasienDto convertPasienToDto(Pasiens pasiens){
        PasienDto pasienDto = new PasienDto();
        pasienDto.setEmail(pasiens.getEmail());
        pasienDto.setPassword(pasiens.getPassword());
        pasienDto.setFirstName(pasiens.getFirstName());
        pasienDto.setLastName(pasiens.getLastName());
        pasienDto.setFullName(pasiens.getFullName());
        pasienDto.setPassword(pasiens.getPassword());
        pasienDto.setAddress(pasiens.getAddress());
        pasienDto.setPhone(pasiens.getPhone());
        return pasienDto;
    }

    private String generatedId(){
        List<Pasiens> all = pasiensRepository.findAll();
        if(all.isEmpty()){
            return "PS001";
        }else {
            Pasiens last = all.getLast();
            return GeneratedId.generatedIdPasiens(last.getId());
        }
    }
}
