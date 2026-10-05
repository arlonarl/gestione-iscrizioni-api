package com.example.progettofinale.data.mapper;

import com.example.progettofinale.data.entity.IscrizioneJpaEntity;
import com.example.progettofinale.domain.model.Iscrizione;

public final class IscrizioneMapper {
    private IscrizioneMapper() {
    }

    public static Iscrizione toDomain(IscrizioneJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        Iscrizione iscrizione = new Iscrizione(
                StudenteMapper.toDomain(entity.getStudente()),
                CorsoMapper.toDomain(entity.getCorso())
        );
        iscrizione.setId(entity.getId());
        return iscrizione;
    }

    public static IscrizioneJpaEntity toEntity(Iscrizione iscrizione) {
        if (iscrizione == null) {
            return null;
        }

        IscrizioneJpaEntity entity = new IscrizioneJpaEntity();
        entity.setId(iscrizione.getId());
        entity.setStudente(StudenteMapper.toEntity(iscrizione.getStudente()));
        entity.setCorso(CorsoMapper.toEntity(iscrizione.getCorso()));
        return entity;
    }
}
