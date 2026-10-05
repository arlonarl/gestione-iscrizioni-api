package com.example.progettofinale.presentation.mapper;

import com.example.progettofinale.domain.model.Docente;
import com.example.progettofinale.presentation.dto.DocenteRequest;
import com.example.progettofinale.presentation.dto.DocenteResponse;

public final class DocenteDtoMapper {
    private DocenteDtoMapper() {
    }

    public static Docente toDomain(DocenteRequest request) {
        return new Docente(
                request.nome(),
                request.cognome(),
                request.email(),
                request.settore()
        );
    }

    public static DocenteResponse toResponse(Docente docente) {
        if (docente == null) {
            return null;
        }

        return new DocenteResponse(
                docente.getId(),
                docente.getNome(),
                docente.getCognome(),
                docente.getEmail(),
                docente.getSettore()
        );
    }
}
