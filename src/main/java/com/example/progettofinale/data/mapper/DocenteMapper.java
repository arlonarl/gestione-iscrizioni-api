package com.example.progettofinale.data.mapper;

import com.example.progettofinale.data.entity.DocenteJpaEntity;
import com.example.progettofinale.domain.model.Docente;

public final class DocenteMapper {
    private DocenteMapper() {
    }

    public static Docente toDomain(DocenteJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        Docente docente = new Docente(
                entity.getNome(),
                entity.getCognome(),
                entity.getEmail(),
                entity.getSettore()
        );
        docente.setId(entity.getId());
        return docente;
    }

    public static DocenteJpaEntity toEntity(Docente docente) {
        if (docente == null) {
            return null;
        }

        DocenteJpaEntity entity = new DocenteJpaEntity();
        entity.setId(docente.getId());
        entity.setNome(docente.getNome());
        entity.setCognome(docente.getCognome());
        entity.setEmail(docente.getEmail());
        entity.setSettore(docente.getSettore());
        return entity;
    }

    public static DocenteJpaEntity reference(Integer id) {
        if (id == null) {
            return null;
        }

        DocenteJpaEntity entity = new DocenteJpaEntity();
        entity.setId(id);
        return entity;
    }
}
