package com.example.progettofinale.data.mapper;

import com.example.progettofinale.data.entity.CorsoJpaEntity;
import com.example.progettofinale.domain.model.Corso;

public final class CorsoMapper {
    private CorsoMapper() {
    }

    public static Corso toDomain(CorsoJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        Corso corso = new Corso(
                entity.getTitolo(),
                entity.getDescrizione(),
                entity.getCfu()
        );
        corso.setId(entity.getId());
        corso.setDocente(DocenteMapper.toDomain(entity.getDocente()));
        return corso;
    }

    public static CorsoJpaEntity toEntity(Corso corso) {
        if (corso == null) {
            return null;
        }

        CorsoJpaEntity entity = new CorsoJpaEntity();
        entity.setId(corso.getId());
        entity.setTitolo(corso.getTitolo());
        entity.setDescrizione(corso.getDescrizione());
        entity.setCfu(corso.getCfu());
        entity.setDocente(DocenteMapper.reference(corso.getDocenteId()));
        return entity;
    }
}
