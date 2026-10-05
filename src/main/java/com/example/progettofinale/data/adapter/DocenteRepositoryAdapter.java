package com.example.progettofinale.data.adapter;

import com.example.progettofinale.data.jpa.DocenteJpaRepository;
import com.example.progettofinale.data.mapper.DocenteMapper;
import com.example.progettofinale.domain.model.Docente;
import com.example.progettofinale.domain.repository.DocenteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DocenteRepositoryAdapter implements DocenteRepository {
    private final DocenteJpaRepository jpaRepository;

    public DocenteRepositoryAdapter(DocenteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Docente> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(DocenteMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Docente> findById(Integer id) {
        return jpaRepository.findById(id)
                .map(DocenteMapper::toDomain);
    }

    @Override
    public Docente save(Docente docente) {
        return DocenteMapper.toDomain(jpaRepository.save(DocenteMapper.toEntity(docente)));
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
