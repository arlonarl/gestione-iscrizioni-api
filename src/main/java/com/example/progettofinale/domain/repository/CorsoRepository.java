package com.example.progettofinale.domain.repository;

import com.example.progettofinale.domain.model.Corso;
import com.example.progettofinale.domain.model.CorsoClassificaIscritti;

import java.util.List;
import java.util.Optional;

public interface CorsoRepository {
    List<Corso> findAll();

    Optional<Corso> findById(Integer id);

    Corso save(Corso corso);

    void deleteById(Integer id);

    List<CorsoClassificaIscritti> findClassificaIscritti();

    boolean existsByDocenteId(Integer docenteId);
}
