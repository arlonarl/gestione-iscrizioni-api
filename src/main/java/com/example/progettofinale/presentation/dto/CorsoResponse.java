package com.example.progettofinale.presentation.dto;

public record CorsoResponse(
        Integer id,
        String titolo,
        String descrizione,
        Integer cfu,
        DocenteResponse docente
) {
}
