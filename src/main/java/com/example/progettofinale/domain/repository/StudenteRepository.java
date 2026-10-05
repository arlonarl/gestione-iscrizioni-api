package com.example.progettofinale.domain.repository;

import com.example.progettofinale.domain.model.Studente;
import com.example.progettofinale.domain.pagination.PageRequest;
import com.example.progettofinale.domain.pagination.PageResult;

import java.util.List;
import java.util.Optional;

public interface StudenteRepository {
    List<Studente> findAll();

    PageResult<Studente> findAll(PageRequest pageRequest);

    Optional<Studente> findById(Integer id);

    Studente save(Studente studente);

    void deleteById(Integer id);

    boolean existsByEmail(String email);

    boolean existsByMatricola(String matricola);
}
