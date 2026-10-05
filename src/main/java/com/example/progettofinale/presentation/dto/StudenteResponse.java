package com.example.progettofinale.presentation.dto;

public record StudenteResponse(
        Integer id,
        String nome,
        String cognome,
        String email,
        String matricola
) {
}
