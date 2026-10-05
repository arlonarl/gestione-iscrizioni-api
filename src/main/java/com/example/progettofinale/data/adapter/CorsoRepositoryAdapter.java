package com.example.progettofinale.data.adapter;

import com.example.progettofinale.data.entity.CorsoJpaEntity;
import com.example.progettofinale.data.jpa.CorsoClassificaIscrittiProjection;
import com.example.progettofinale.data.jpa.CorsoJpaRepository;
import com.example.progettofinale.data.jpa.DocenteJpaRepository;
import com.example.progettofinale.data.mapper.CorsoMapper;
import com.example.progettofinale.domain.model.Corso;
import com.example.progettofinale.domain.model.CorsoClassificaIscritti;
import com.example.progettofinale.domain.repository.CorsoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CorsoRepositoryAdapter implements CorsoRepository {
    private final CorsoJpaRepository jpaRepository;
    private final DocenteJpaRepository docenteJpaRepository;

    public CorsoRepositoryAdapter(CorsoJpaRepository jpaRepository, DocenteJpaRepository docenteJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.docenteJpaRepository = docenteJpaRepository;
    }

    @Override
    public List<Corso> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(CorsoMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Corso> findById(Integer id) {
        return jpaRepository.findById(id)
                .map(CorsoMapper::toDomain);
    }

    @Override
    public Corso save(Corso corso) {
        CorsoJpaEntity entity = CorsoMapper.toEntity(corso);
        if (corso.getDocenteId() != null) {
            entity.setDocente(docenteJpaRepository.getReferenceById(corso.getDocenteId()));
        }
        return CorsoMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<CorsoClassificaIscritti> findClassificaIscritti() {
        return jpaRepository.findClassificaIscrittiNative()
                .stream()
                .map(this::toCorsoClassificaIscritti)
                .toList();
    }

    private CorsoClassificaIscritti toCorsoClassificaIscritti(CorsoClassificaIscrittiProjection projection) {
        CorsoJpaEntity entity = new CorsoJpaEntity();
        entity.setId(projection.getId());
        entity.setTitolo(projection.getTitolo());
        entity.setDescrizione(projection.getDescrizione());
        entity.setCfu(projection.getCfu());

        return new CorsoClassificaIscritti(CorsoMapper.toDomain(entity), projection.getNumeroIscritti());
    }

    @Override
    public boolean existsByDocenteId(Integer docenteId) {
        return jpaRepository.existsByDocente_Id(docenteId);
    }
}
