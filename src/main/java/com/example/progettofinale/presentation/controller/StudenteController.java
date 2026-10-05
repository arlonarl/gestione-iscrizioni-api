package com.example.progettofinale.presentation.controller;

import com.example.progettofinale.domain.model.Studente;
import com.example.progettofinale.domain.pagination.PageRequest;
import com.example.progettofinale.domain.service.IscrizioneService;
import com.example.progettofinale.domain.service.StudenteService;
import com.example.progettofinale.presentation.dto.CfuTotaliResponse;
import com.example.progettofinale.presentation.dto.CorsoResponse;
import com.example.progettofinale.presentation.dto.PageResponse;
import com.example.progettofinale.presentation.dto.StudenteRequest;
import com.example.progettofinale.presentation.dto.StudenteResponse;
import com.example.progettofinale.presentation.mapper.CorsoDtoMapper;
import com.example.progettofinale.presentation.mapper.StudenteDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/studenti")
public class StudenteController {
    private final StudenteService studenteService;
    private final IscrizioneService iscrizioneService;

    public StudenteController(StudenteService studenteService, IscrizioneService iscrizioneService) {
        this.studenteService = studenteService;
        this.iscrizioneService = iscrizioneService;
    }

    @GetMapping
    public PageResponse<StudenteResponse> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var result = studenteService.findAll(new PageRequest(page, size));
        return new PageResponse<>(
                result.content()
                .stream()
                .map(StudenteDtoMapper::toResponse)
                        .toList(),
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages()
        );
    }

    @GetMapping("/{id}")
    public StudenteResponse findById(@PathVariable Integer id) {
        return StudenteDtoMapper.toResponse(studenteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<StudenteResponse> create(@Valid @RequestBody StudenteRequest request) {
        Studente created = studenteService.create(StudenteDtoMapper.toDomain(request));
        return ResponseEntity
                .created(URI.create("/api/studenti/" + created.getId()))
                .body(StudenteDtoMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public StudenteResponse update(@PathVariable Integer id, @Valid @RequestBody StudenteRequest request) {
        return StudenteDtoMapper.toResponse(studenteService.update(id, StudenteDtoMapper.toDomain(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        studenteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/corsi/{corsoId}")
    public ResponseEntity<Void> iscriviStudenteACorso(@PathVariable Integer id, @PathVariable Integer corsoId) {
        iscrizioneService.iscriviStudenteACorso(id, corsoId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/corsi/{corsoId}")
    public ResponseEntity<Void> rimuoviIscrizione(@PathVariable Integer id, @PathVariable Integer corsoId) {
        iscrizioneService.rimuoviIscrizione(id, corsoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/corsi")
    public Map<String, Object> findCorsiStudente(@PathVariable Integer id) {
        StudenteResponse studente = StudenteDtoMapper.toResponse(studenteService.findById(id));
        List<CorsoResponse> corsi = iscrizioneService.findCorsiStudente(id)
                .stream()
                .map(CorsoDtoMapper::toResponse)
                .toList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("studente", studente);
        response.put("corsi", corsi);
        return response;
    }

    @GetMapping("/{id}/cfu-totali")
    public CfuTotaliResponse calcolaCfuTotali(@PathVariable Integer id) {
        return new CfuTotaliResponse(id, iscrizioneService.calcolaCfuTotali(id));
    }
}
