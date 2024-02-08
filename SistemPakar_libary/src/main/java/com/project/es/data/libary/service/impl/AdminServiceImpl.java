package com.project.es.data.libary.service.impl;

import com.project.es.data.libary.Util.GeneratedId;
import com.project.es.data.libary.dto.AdminDto;
import com.project.es.data.libary.dto.AdminPasswordChangeDto;
import com.project.es.data.libary.entity.Admin;
import com.project.es.data.libary.repository.AdminRepository;
import com.project.es.data.libary.repository.RoleRepository;
import com.project.es.data.libary.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }



    @Override
    public Admin saveAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setId(generatedId());
        admin.setPassword(adminDto.getPassword());
        admin.setEmail(adminDto.getEmail());
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setFullName(adminDto.getFullName());
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    @Override
    public Long countAdmin() {
        return adminRepository.count();
    }

    @Override
    public AdminDto findByEmailToDto(String email) {
        Admin admin = adminRepository.findByEmail(email);
        AdminDto adminDto = new AdminDto();
        adminDto.setEmail(admin.getEmail());
        adminDto.setFirstName(admin.getFirstName());
        adminDto.setLastName(admin.getLastName());
        adminDto.setFullName(admin.getFullName());
        adminDto.setPassword(admin.getPassword());
        return adminDto;
    }

    @Override
    public Admin updateAdminProfile(AdminDto adminDto) {
        Admin admin = adminRepository.findByEmail(adminDto.getEmail());
        admin.setUpdatedAt(LocalDateTime.now());
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setFullName(adminDto.getFullName());
        return adminRepository.save(admin);
    }

    @Override
    public boolean passwordChanger(AdminPasswordChangeDto adminPasswordChangeDto) {
        boolean isSuccess = false;
        Admin admin = adminRepository.findByEmail(adminPasswordChangeDto.getEmail());
        if(bCryptPasswordEncoder.matches(adminPasswordChangeDto.getOldPassword(),admin.getPassword()) == true){
            admin.setPassword(bCryptPasswordEncoder.encode(adminPasswordChangeDto.getNewPassword()));
            adminRepository.save(admin);
            isSuccess = true;
        }else {
            isSuccess = false;
        }
        return isSuccess;
    }


    private String generatedId(){
        List<Admin> all = adminRepository.findAll();
        if(all.isEmpty()){
            return "AD001";
        }else {
            Admin last = all.getLast();
            return GeneratedId.generatedIdAdmin(last.getId());
        }
    }
}
