package com.example.progettofinale.data.jpa;

import com.example.progettofinale.data.entity.StudenteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudenteJpaRepository extends JpaRepository<StudenteJpaEntity, Integer> {
    boolean existsByEmail(String email);

    boolean existsByMatricola(String matricola);
}
