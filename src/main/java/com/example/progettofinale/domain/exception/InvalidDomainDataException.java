package com.example.progettofinale.domain.exception;

public class InvalidDomainDataException extends RuntimeException {
    public InvalidDomainDataException(String message) {
        super(message);
    }
}
