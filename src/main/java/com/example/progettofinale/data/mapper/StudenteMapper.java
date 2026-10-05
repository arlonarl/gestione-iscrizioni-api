package com.example.progettofinale.data.mapper;

import com.example.progettofinale.data.entity.StudenteJpaEntity;
import com.example.progettofinale.domain.model.Studente;

public final class StudenteMapper {
    private StudenteMapper() {
    }

    public static Studente toDomain(StudenteJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        Studente studente = new Studente(
                entity.getNome(),
                entity.getCognome(),
                entity.getEmail(),
                entity.getMatricola()
        );
        studente.setId(entity.getId());
        return studente;
    }

    public static StudenteJpaEntity toEntity(Studente studente) {
        if (studente == null) {
            return null;
        }

        StudenteJpaEntity entity = new StudenteJpaEntity();
        entity.setId(studente.getId());
        entity.setNome(studente.getNome());
        entity.setCognome(studente.getCognome());
        entity.setEmail(studente.getEmail());
        entity.setMatricola(studente.getMatricola());
        return entity;
    }
}
