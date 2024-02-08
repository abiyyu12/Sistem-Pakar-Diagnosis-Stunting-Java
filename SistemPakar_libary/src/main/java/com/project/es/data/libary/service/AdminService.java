package com.project.es.data.libary.service;

import com.project.es.data.libary.dto.AdminDto;
import com.project.es.data.libary.dto.AdminPasswordChangeDto;
import com.project.es.data.libary.entity.Admin;

public interface AdminService {

    Admin findByEmail(String email);
    Admin saveAdmin(AdminDto adminDto);

    Long countAdmin();

    AdminDto findByEmailToDto(String email);

    Admin updateAdminProfile(AdminDto adminDto);

    boolean passwordChanger(AdminPasswordChangeDto adminPasswordChangeDto);
}
