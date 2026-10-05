package com.example.progettofinale.domain.service;

import com.example.progettofinale.domain.exception.DuplicateResourceException;
import com.example.progettofinale.domain.exception.ResourceNotFoundException;
import com.example.progettofinale.domain.model.Docente;
import com.example.progettofinale.domain.repository.CorsoRepository;
import com.example.progettofinale.domain.repository.DocenteRepository;

import java.util.List;
import java.util.Objects;

public class DocenteService {
    private final DocenteRepository docenteRepository;
    private final CorsoRepository corsoRepository;

    public DocenteService(DocenteRepository docenteRepository, CorsoRepository corsoRepository) {
        this.docenteRepository = docenteRepository;
        this.corsoRepository = corsoRepository;
    }

    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    public Docente findById(Integer id) {
        return docenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Docente non trovato"));
    }

    public Docente create(Docente docente) {
        ensureEmailIsUnique(docente.getEmail());
        return docenteRepository.save(docente);
    }

    public Docente update(Integer id, Docente docente) {
        Docente current = findById(id);

        if (!Objects.equals(current.getEmail(), docente.getEmail())) {
            ensureEmailIsUnique(docente.getEmail());
        }

        current.setNome(docente.getNome());
        current.setCognome(docente.getCognome());
        current.setEmail(docente.getEmail());
        current.setSettore(docente.getSettore());
        return docenteRepository.save(current);
    }

    public void delete(Integer id) {
        findById(id);
        docenteRepository.deleteById(id);
    }

    public boolean hasCorsi(Integer id) {
        findById(id);
        return corsoRepository.existsByDocenteId(id);
    }

    private void ensureEmailIsUnique(String email) {
        if (docenteRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email docente gia' utilizzata");
        }
    }
}
