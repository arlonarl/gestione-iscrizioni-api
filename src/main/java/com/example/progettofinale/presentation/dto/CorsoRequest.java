package com.example.progettofinale.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CorsoRequest(
        @NotBlank(message = "Il titolo e' obbligatorio")
        @Size(max = 30, message = "Il titolo non puo' superare 30 caratteri")
        String titolo,

        @Size(max = 255, message = "La descrizione non puo' superare 255 caratteri")
        String descrizione,

        @NotNull(message = "I CFU sono obbligatori")
        @Positive(message = "I CFU devono essere un intero positivo")
        Integer cfu,

        @Positive(message = "Il docente deve avere un id positivo")
        Integer docenteId
) {
}
