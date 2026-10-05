package com.example.progettofinale.core.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public record ErrorResponse(
        String message,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        Map<String, String> errors
) {
    public static ErrorResponse of(String message) {
        return new ErrorResponse(message, Map.of());
    }

    public static ErrorResponse of(String message, Map<String, String> errors) {
        return new ErrorResponse(message, errors);
    }
}
