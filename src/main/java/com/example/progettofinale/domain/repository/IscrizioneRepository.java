package com.example.progettofinale.domain.repository;

import com.example.progettofinale.domain.model.Corso;
import com.example.progettofinale.domain.model.Iscrizione;

import java.util.List;
import java.util.Optional;

public interface IscrizioneRepository {
    Iscrizione save(Iscrizione iscrizione);

    Optional<Iscrizione> findByStudenteIdAndCorsoId(Integer studenteId, Integer corsoId);

    List<Corso> findCorsiByStudenteId(Integer studenteId);

    void delete(Iscrizione iscrizione);

    boolean existsByStudenteIdAndCorsoId(Integer studenteId, Integer corsoId);

    Integer sumCfuByStudenteId(Integer studenteId);
}
