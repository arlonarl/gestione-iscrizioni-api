package com.example.progettofinale.domain.service;

import com.example.progettofinale.domain.exception.DuplicateResourceException;
import com.example.progettofinale.domain.exception.ResourceNotFoundException;
import com.example.progettofinale.domain.model.Corso;
import com.example.progettofinale.domain.model.Iscrizione;
import com.example.progettofinale.domain.model.Studente;
import com.example.progettofinale.domain.repository.CorsoRepository;
import com.example.progettofinale.domain.repository.IscrizioneRepository;
import com.example.progettofinale.domain.repository.StudenteRepository;

import java.util.List;

public class IscrizioneService {
    private final IscrizioneRepository iscrizioneRepository;
    private final StudenteRepository studenteRepository;
    private final CorsoRepository corsoRepository;

    public IscrizioneService(
            IscrizioneRepository iscrizioneRepository,
            StudenteRepository studenteRepository,
            CorsoRepository corsoRepository
    ) {
        this.iscrizioneRepository = iscrizioneRepository;
        this.studenteRepository = studenteRepository;
        this.corsoRepository = corsoRepository;
    }

    public Iscrizione iscriviStudenteACorso(Integer studenteId, Integer corsoId) {
        Studente studente = findStudenteOrThrow(studenteId);
        Corso corso = findCorsoOrThrow(corsoId);

        if (iscrizioneRepository.existsByStudenteIdAndCorsoId(studenteId, corsoId)) {
            throw new DuplicateResourceException("Studente gia' iscritto al corso");
        }

        return iscrizioneRepository.save(new Iscrizione(studente, corso));
    }

    public void rimuoviIscrizione(Integer studenteId, Integer corsoId) {
        findStudenteOrThrow(studenteId);
        findCorsoOrThrow(corsoId);

        Iscrizione iscrizione = iscrizioneRepository.findByStudenteIdAndCorsoId(studenteId, corsoId)
                .orElseThrow(() -> new ResourceNotFoundException("Iscrizione non trovata"));

        iscrizioneRepository.delete(iscrizione);
    }

    public List<Corso> findCorsiStudente(Integer studenteId) {
        findStudenteOrThrow(studenteId);
        return iscrizioneRepository.findCorsiByStudenteId(studenteId);
    }

    public Integer calcolaCfuTotali(Integer studenteId) {
        findStudenteOrThrow(studenteId);
        return iscrizioneRepository.sumCfuByStudenteId(studenteId);
    }

    private Studente findStudenteOrThrow(Integer studenteId) {
        return studenteRepository.findById(studenteId)
                .orElseThrow(() -> new ResourceNotFoundException("Studente non trovato"));
    }

    private Corso findCorsoOrThrow(Integer corsoId) {
        return corsoRepository.findById(corsoId)
                .orElseThrow(() -> new ResourceNotFoundException("Corso non trovato"));
    }
}
