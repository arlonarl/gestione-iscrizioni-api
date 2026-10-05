package com.example.progettofinale.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StudenteRequest(
        @NotBlank(message = "Il nome e' obbligatorio")
        @Size(max = 30, message = "Il nome non puo' superare 30 caratteri")
        String nome,

        @NotBlank(message = "Il cognome e' obbligatorio")
        @Size(max = 30, message = "Il cognome non puo' superare 30 caratteri")
        String cognome,

        @NotBlank(message = "L'email e' obbligatoria")
        @Email(message = "L'email non e' valida")
        @Size(max = 50, message = "L'email non puo' superare 50 caratteri")
        String email,

        @NotBlank(message = "La matricola e' obbligatoria")
        @Size(max = 50, message = "La matricola non puo' superare 50 caratteri")
        String matricola
) {
}
