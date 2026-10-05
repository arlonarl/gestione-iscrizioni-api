package com.example.progettofinale.domain.service;

import com.example.progettofinale.domain.exception.InvalidDomainDataException;
import com.example.progettofinale.domain.exception.ResourceNotFoundException;
import com.example.progettofinale.domain.model.Corso;
import com.example.progettofinale.domain.model.CorsoClassificaIscritti;
import com.example.progettofinale.domain.repository.CorsoRepository;
import com.example.progettofinale.domain.repository.DocenteRepository;

import java.util.List;

public class CorsoService {
    private final CorsoRepository corsoRepository;
    private final DocenteRepository docenteRepository;

    public CorsoService(CorsoRepository corsoRepository, DocenteRepository docenteRepository) {
        this.corsoRepository = corsoRepository;
        this.docenteRepository = docenteRepository;
    }

    public List<Corso> findAll() {
        return corsoRepository.findAll();
    }

    public Corso findById(Integer id) {
        return corsoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Corso non trovato"));
    }

    public Corso create(Corso corso) {
        ensureCfuIsPositive(corso);
        ensureDocenteExistsIfPresent(corso);
        return corsoRepository.save(corso);
    }

    public Corso update(Integer id, Corso corso) {
        Corso current = findById(id);
        ensureCfuIsPositive(corso);
        ensureDocenteExistsIfPresent(corso);

        current.setTitolo(corso.getTitolo());
        current.setDescrizione(corso.getDescrizione());
        current.setCfu(corso.getCfu());
        current.setDocente(null);
        current.setDocenteId(corso.getDocenteId());
        return corsoRepository.save(current);
    }

    public void delete(Integer id) {
        findById(id);
        corsoRepository.deleteById(id);
    }

    public List<CorsoClassificaIscritti> classificaIscritti() {
        return corsoRepository.findClassificaIscritti();
    }

    private void ensureCfuIsPositive(Corso corso) {
        if (corso.getCfu() == null || corso.getCfu() <= 0) {
            throw new InvalidDomainDataException("I CFU devono essere un intero positivo");
        }
    }

    private void ensureDocenteExistsIfPresent(Corso corso) {
        Integer docenteId = corso.getDocenteId();
        if (docenteId != null && docenteRepository.findById(docenteId).isEmpty()) {
            throw new ResourceNotFoundException("Docente non trovato");
        }
    }
}
