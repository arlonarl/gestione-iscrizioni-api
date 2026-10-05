package com.example.progettofinale.domain.service;

import com.example.progettofinale.domain.exception.DuplicateResourceException;
import com.example.progettofinale.domain.exception.ResourceNotFoundException;
import com.example.progettofinale.domain.model.Studente;
import com.example.progettofinale.domain.pagination.PageRequest;
import com.example.progettofinale.domain.pagination.PageResult;
import com.example.progettofinale.domain.repository.StudenteRepository;

import java.util.List;
import java.util.Objects;

public class StudenteService {
    private final StudenteRepository studenteRepository;

    public StudenteService(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    public List<Studente> findAll() {
        return studenteRepository.findAll();
    }

    public PageResult<Studente> findAll(PageRequest pageRequest) {
        return studenteRepository.findAll(pageRequest);
    }

    public Studente findById(Integer id) {
        return studenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Studente non trovato"));
    }

    public Studente create(Studente studente) {
        ensureEmailIsUnique(studente.getEmail());
        ensureMatricolaIsUnique(studente.getMatricola());
        return studenteRepository.save(studente);
    }

    public Studente update(Integer id, Studente studente) {
        Studente current = findById(id);

        if (!Objects.equals(current.getEmail(), studente.getEmail())) {
            ensureEmailIsUnique(studente.getEmail());
        }
        if (!Objects.equals(current.getMatricola(), studente.getMatricola())) {
            ensureMatricolaIsUnique(studente.getMatricola());
        }

        current.setNome(studente.getNome());
        current.setCognome(studente.getCognome());
        current.setEmail(studente.getEmail());
        current.setMatricola(studente.getMatricola());
        return studenteRepository.save(current);
    }

    public void delete(Integer id) {
        findById(id);
        studenteRepository.deleteById(id);
    }

    private void ensureEmailIsUnique(String email) {
        if (studenteRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email gia' utilizzata");
        }
    }

    private void ensureMatricolaIsUnique(String matricola) {
        if (studenteRepository.existsByMatricola(matricola)) {
            throw new DuplicateResourceException("Matricola gia' utilizzata");
        }
    }
}
