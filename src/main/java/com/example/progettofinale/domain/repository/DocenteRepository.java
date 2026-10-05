package com.example.progettofinale.domain.repository;

import com.example.progettofinale.domain.model.Docente;

import java.util.List;
import java.util.Optional;

public interface DocenteRepository {
    List<Docente> findAll();

    Optional<Docente> findById(Integer id);

    Docente save(Docente docente);

    void deleteById(Integer id);

    boolean existsByEmail(String email);
}
