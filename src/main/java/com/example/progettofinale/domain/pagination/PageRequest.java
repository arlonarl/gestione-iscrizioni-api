package com.example.progettofinale.domain.pagination;

public record PageRequest(int page, int size) {
    public PageRequest {
        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 10;
        }
        if (size > 100) {
            size = 100;
        }
    }
}
