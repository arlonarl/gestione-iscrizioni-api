package com.example.progettofinale.data.adapter;

import com.example.progettofinale.data.entity.IscrizioneJpaEntity;
import com.example.progettofinale.data.jpa.CorsoJpaRepository;
import com.example.progettofinale.data.jpa.IscrizioneJpaRepository;
import com.example.progettofinale.data.jpa.StudenteJpaRepository;
import com.example.progettofinale.data.mapper.CorsoMapper;
import com.example.progettofinale.data.mapper.IscrizioneMapper;
import com.example.progettofinale.domain.model.Corso;
import com.example.progettofinale.domain.model.Iscrizione;
import com.example.progettofinale.domain.repository.IscrizioneRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IscrizioneRepositoryAdapter implements IscrizioneRepository {
    private final IscrizioneJpaRepository jpaRepository;
    private final StudenteJpaRepository studenteJpaRepository;
    private final CorsoJpaRepository corsoJpaRepository;

    public IscrizioneRepositoryAdapter(
            IscrizioneJpaRepository jpaRepository,
            StudenteJpaRepository studenteJpaRepository,
            CorsoJpaRepository corsoJpaRepository
    ) {
        this.jpaRepository = jpaRepository;
        this.studenteJpaRepository = studenteJpaRepository;
        this.corsoJpaRepository = corsoJpaRepository;
    }

    @Override
    public Iscrizione save(Iscrizione iscrizione) {
        IscrizioneJpaEntity entity = new IscrizioneJpaEntity();
        entity.setId(iscrizione.getId());
        entity.setStudente(studenteJpaRepository.getReferenceById(iscrizione.getStudente().getId()));
        entity.setCorso(corsoJpaRepository.getReferenceById(iscrizione.getCorso().getId()));
        return IscrizioneMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Iscrizione> findByStudenteIdAndCorsoId(Integer studenteId, Integer corsoId) {
        return jpaRepository.findByStudente_IdAndCorso_Id(studenteId, corsoId)
                .map(IscrizioneMapper::toDomain);
    }

    @Override
    public List<Corso> findCorsiByStudenteId(Integer studenteId) {
        return jpaRepository.findCorsiByStudenteId(studenteId)
                .stream()
                .map(CorsoMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Iscrizione iscrizione) {
        jpaRepository.deleteById(iscrizione.getId());
    }

    @Override
    public boolean existsByStudenteIdAndCorsoId(Integer studenteId, Integer corsoId) {
        return jpaRepository.existsByStudente_IdAndCorso_Id(studenteId, corsoId);
    }

    @Override
    public Integer sumCfuByStudenteId(Integer studenteId) {
        return jpaRepository.sumCfuByStudenteId(studenteId);
    }
}
