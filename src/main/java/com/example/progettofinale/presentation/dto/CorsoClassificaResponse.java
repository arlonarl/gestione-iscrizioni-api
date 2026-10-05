package com.example.progettofinale.presentation.dto;

public record CorsoClassificaResponse(
        Integer id,
        String titolo,
        String descrizione,
        Integer cfu,
        Integer numeroIscritti
) {
}
