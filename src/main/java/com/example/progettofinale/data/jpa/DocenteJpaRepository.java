package com.example.progettofinale.data.jpa;

import com.example.progettofinale.data.entity.DocenteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocenteJpaRepository extends JpaRepository<DocenteJpaEntity, Integer> {
    boolean existsByEmail(String email);
}
