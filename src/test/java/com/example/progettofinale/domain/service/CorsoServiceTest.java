package com.example.progettofinale.domain.service;

import com.example.progettofinale.domain.exception.ResourceNotFoundException;
import com.example.progettofinale.domain.model.Corso;
import com.example.progettofinale.domain.model.CorsoClassificaIscritti;
import com.example.progettofinale.domain.model.Docente;
import com.example.progettofinale.domain.repository.CorsoRepository;
import com.example.progettofinale.domain.repository.DocenteRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CorsoServiceTest {
    private final InMemoryCorsoRepository corsoRepository = new InMemoryCorsoRepository();
    private final InMemoryDocenteRepository docenteRepository = new InMemoryDocenteRepository();
    private final CorsoService corsoService = new CorsoService(corsoRepository, docenteRepository);

    @Test
    void createAllowsCourseWithoutDocente() {
        assertDoesNotThrow(() -> corsoService.create(new Corso("Java", "Base", 8, null)));
    }

    @Test
    void createRejectsMissingDocenteWhenDocenteIdIsProvided() {
        Corso corso = new Corso("Java", "Base", 8, 99);

        assertThrows(ResourceNotFoundException.class, () -> corsoService.create(corso));
    }

    @Test
    void createAllowsExistingDocente() {
        docenteRepository.save(new Docente("Ada", "Lovelace", "ada@example.com", "Programmazione"));

        assertDoesNotThrow(() -> corsoService.create(new Corso("Java", "Base", 8, 1)));
    }

    private static class InMemoryCorsoRepository implements CorsoRepository {
        private final List<Corso> corsi = new ArrayList<>();

        @Override
        public List<Corso> findAll() {
            return corsi;
        }

        @Override
        public Optional<Corso> findById(Integer id) {
            return corsi.stream()
                    .filter(corso -> corso.getId().equals(id))
                    .findFirst();
        }

        @Override
        public Corso save(Corso corso) {
            if (corso.getId() == null) {
                corso.setId(corsi.size() + 1);
            }
            corsi.add(corso);
            return corso;
        }

        @Override
        public void deleteById(Integer id) {
            corsi.removeIf(corso -> corso.getId().equals(id));
        }

        @Override
        public List<CorsoClassificaIscritti> findClassificaIscritti() {
            return List.of();
        }

        @Override
        public boolean existsByDocenteId(Integer docenteId) {
            return corsi.stream().anyMatch(corso -> docenteId.equals(corso.getDocenteId()));
        }
    }

    private static class InMemoryDocenteRepository implements DocenteRepository {
        private final List<Docente> docenti = new ArrayList<>();

        @Override
        public List<Docente> findAll() {
            return docenti;
        }

        @Override
        public Optional<Docente> findById(Integer id) {
            return docenti.stream()
                    .filter(docente -> docente.getId().equals(id))
                    .findFirst();
        }

        @Override
        public Docente save(Docente docente) {
            if (docente.getId() == null) {
                docente.setId(docenti.size() + 1);
            }
            docenti.add(docente);
            return docente;
        }

        @Override
        public void deleteById(Integer id) {
            docenti.removeIf(docente -> docente.getId().equals(id));
        }

        @Override
        public boolean existsByEmail(String email) {
            return docenti.stream().anyMatch(docente -> docente.getEmail().equals(email));
        }
    }
}
