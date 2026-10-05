package com.example.progettofinale.data.jpa;

import com.example.progettofinale.data.entity.CorsoJpaEntity;
import com.example.progettofinale.data.entity.IscrizioneJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IscrizioneJpaRepository extends JpaRepository<IscrizioneJpaEntity, Integer> {
    Optional<IscrizioneJpaEntity> findByStudente_IdAndCorso_Id(Integer studenteId, Integer corsoId);

    boolean existsByStudente_IdAndCorso_Id(Integer studenteId, Integer corsoId);

    @Query("""
            SELECT i.corso
            FROM IscrizioneJpaEntity i
            WHERE i.studente.id = :studenteId
            """)
    List<CorsoJpaEntity> findCorsiByStudenteId(@Param("studenteId") Integer studenteId);

    @Query("""
            SELECT CAST(COALESCE(SUM(i.corso.cfu), 0) AS integer)
            FROM IscrizioneJpaEntity i
            WHERE i.studente.id = :studenteId
            """)
    Integer sumCfuByStudenteId(@Param("studenteId") Integer studenteId);
}
