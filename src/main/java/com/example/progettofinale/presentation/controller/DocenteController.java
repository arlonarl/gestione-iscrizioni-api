package com.example.progettofinale.presentation.controller;

import com.example.progettofinale.domain.model.Docente;
import com.example.progettofinale.domain.service.DocenteService;
import com.example.progettofinale.presentation.dto.DocenteRequest;
import com.example.progettofinale.presentation.dto.DocenteResponse;
import com.example.progettofinale.presentation.mapper.DocenteDtoMapper;
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
@RequestMapping("/api/docenti")
public class DocenteController {
    private final DocenteService docenteService;

    public DocenteController(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    @GetMapping
    public List<DocenteResponse> findAll() {
        return docenteService.findAll()
                .stream()
                .map(DocenteDtoMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public DocenteResponse findById(@PathVariable Integer id) {
        return DocenteDtoMapper.toResponse(docenteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DocenteResponse> create(@Valid @RequestBody DocenteRequest request) {
        Docente created = docenteService.create(DocenteDtoMapper.toDomain(request));
        return ResponseEntity
                .created(URI.create("/api/docenti/" + created.getId()))
                .body(DocenteDtoMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public DocenteResponse update(@PathVariable Integer id, @Valid @RequestBody DocenteRequest request) {
        return DocenteDtoMapper.toResponse(docenteService.update(id, DocenteDtoMapper.toDomain(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        docenteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
