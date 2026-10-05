package com.example.progettofinale.presentation.mapper;

import com.example.progettofinale.domain.model.Studente;
import com.example.progettofinale.presentation.dto.StudenteRequest;
import com.example.progettofinale.presentation.dto.StudenteResponse;

public final class StudenteDtoMapper {
    private StudenteDtoMapper() {
    }

    public static Studente toDomain(StudenteRequest request) {
        return new Studente(
                request.nome(),
                request.cognome(),
                request.email(),
                request.matricola()
        );
    }

    public static StudenteResponse toResponse(Studente studente) {
        return new StudenteResponse(
                studente.getId(),
                studente.getNome(),
                studente.getCognome(),
                studente.getEmail(),
                studente.getMatricola()
        );
    }
}
