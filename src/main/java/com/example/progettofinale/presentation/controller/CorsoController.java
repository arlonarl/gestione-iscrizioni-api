package com.example.progettofinale.presentation.controller;

import com.example.progettofinale.domain.model.Corso;
import com.example.progettofinale.domain.service.CorsoService;
import com.example.progettofinale.presentation.dto.CorsoClassificaResponse;
import com.example.progettofinale.presentation.dto.CorsoRequest;
import com.example.progettofinale.presentation.dto.CorsoResponse;
import com.example.progettofinale.presentation.mapper.CorsoDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/corsi")
public class CorsoController {
    private final CorsoService corsoService;

    public CorsoController(CorsoService corsoService) {
        this.corsoService = corsoService;
    }

    @GetMapping
    public List<CorsoResponse> findAll() {
        return corsoService.findAll()
                .stream()
                .map(CorsoDtoMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public CorsoResponse findById(@PathVariable Integer id) {
        return CorsoDtoMapper.toResponse(corsoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CorsoResponse> create(@Valid @RequestBody CorsoRequest request) {
        Corso created = corsoService.create(CorsoDtoMapper.toDomain(request));
        return ResponseEntity
                .created(URI.create("/api/corsi/" + created.getId()))
                .body(CorsoDtoMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public CorsoResponse update(@PathVariable Integer id, @Valid @RequestBody CorsoRequest request) {
        return CorsoDtoMapper.toResponse(corsoService.update(id, CorsoDtoMapper.toDomain(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        corsoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/classifica-iscritti")
    public List<CorsoClassificaResponse> classificaIscritti() {
        return corsoService.classificaIscritti()
                .stream()
                .map(CorsoDtoMapper::toClassificaResponse)
                .toList();
    }
}
