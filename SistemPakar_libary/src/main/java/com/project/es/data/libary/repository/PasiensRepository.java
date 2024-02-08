package com.project.es.data.libary.repository;

import com.project.es.data.libary.entity.Pasiens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasiensRepository extends JpaRepository<Pasiens,String> {
    Pasiens findByEmail(String email);
}
