package com.project.es.data.libary.repository;

import com.project.es.data.libary.entity.Penyakit;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PenyakitRepository extends JpaRepository<Penyakit,String> {
    Penyakit findByNamaPenyakit(String namaPenyakit);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Penyakit p WHERE p.id = ?1")
    Optional<Penyakit> findLockingById(String id);
}
