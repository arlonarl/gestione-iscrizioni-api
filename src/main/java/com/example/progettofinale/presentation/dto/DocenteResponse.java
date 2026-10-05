package com.example.progettofinale.presentation.dto;

public record DocenteResponse(
        Integer id,
        String nome,
        String cognome,
        String email,
        String settore
) {
}
