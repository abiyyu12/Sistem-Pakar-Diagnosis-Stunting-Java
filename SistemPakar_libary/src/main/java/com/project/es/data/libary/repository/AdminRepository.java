package com.project.es.data.libary.repository;

import com.project.es.data.libary.dto.AdminDto;
import com.project.es.data.libary.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,String> {
    Admin findByEmail(String email);

}