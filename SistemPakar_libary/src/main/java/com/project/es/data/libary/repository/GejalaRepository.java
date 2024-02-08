package com.project.es.data.libary.repository;

import com.project.es.data.libary.entity.Gejala;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GejalaRepository extends JpaRepository<Gejala,String> {

    Gejala findByNamaGejala(String namaGejala);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT g FROM Gejala g WHERE g.id = ?1")
    Optional<Gejala> findLockingById(String id);
}
