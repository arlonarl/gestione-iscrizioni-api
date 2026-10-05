package com.example.progettofinale.data.adapter;

import com.example.progettofinale.data.jpa.StudenteJpaRepository;
import com.example.progettofinale.data.mapper.StudenteMapper;
import com.example.progettofinale.domain.model.Studente;
import com.example.progettofinale.domain.pagination.PageRequest;
import com.example.progettofinale.domain.pagination.PageResult;
import com.example.progettofinale.domain.repository.StudenteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudenteRepositoryAdapter implements StudenteRepository {
    private final StudenteJpaRepository jpaRepository;

    public StudenteRepositoryAdapter(StudenteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Studente> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(StudenteMapper::toDomain)
                .toList();
    }

    @Override
    public PageResult<Studente> findAll(PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.page(), pageRequest.size());
        Page<Studente> page = jpaRepository.findAll(pageable)
                .map(StudenteMapper::toDomain);

        return new PageResult<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public Optional<Studente> findById(Integer id) {
        return jpaRepository.findById(id)
                .map(StudenteMapper::toDomain);
    }

    @Override
    public Studente save(Studente studente) {
        return StudenteMapper.toDomain(jpaRepository.save(StudenteMapper.toEntity(studente)));
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByMatricola(String matricola) {
        return jpaRepository.existsByMatricola(matricola);
    }
}
