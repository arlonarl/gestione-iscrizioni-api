package com.example.progettofinale.domain.service;

import com.example.progettofinale.domain.exception.DuplicateResourceException;
import com.example.progettofinale.domain.exception.ResourceNotFoundException;
import com.example.progettofinale.domain.model.Corso;
import com.example.progettofinale.domain.model.CorsoClassificaIscritti;
import com.example.progettofinale.domain.model.Iscrizione;
import com.example.progettofinale.domain.model.Studente;
import com.example.progettofinale.domain.pagination.PageRequest;
import com.example.progettofinale.domain.pagination.PageResult;
import com.example.progettofinale.domain.repository.CorsoRepository;
import com.example.progettofinale.domain.repository.IscrizioneRepository;
import com.example.progettofinale.domain.repository.StudenteRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IscrizioneServiceTest {
    private final InMemoryIscrizioneRepository iscrizioneRepository = new InMemoryIscrizioneRepository();
    private final InMemoryStudenteRepository studenteRepository = new InMemoryStudenteRepository();
    private final InMemoryCorsoRepository corsoRepository = new InMemoryCorsoRepository();
    private final IscrizioneService iscrizioneService = new IscrizioneService(
            iscrizioneRepository,
            studenteRepository,
            corsoRepository
    );

    @Test
    void iscriviStudenteACorsoCreatesEnrollmentWhenResourcesExist() {
        Studente studente = studenteRepository.save(new Studente("Ada", "Lovelace", "ada@example.com", "S001"));
        Corso corso = corsoRepository.save(new Corso("Java", "Base", 8, null));

        Iscrizione iscrizione = iscrizioneService.iscriviStudenteACorso(studente.getId(), corso.getId());

        assertEquals(studente.getId(), iscrizione.getStudente().getId());
        assertEquals(corso.getId(), iscrizione.getCorso().getId());
        assertEquals(1, iscrizioneRepository.findAll().size());
    }

    @Test
    void iscriviStudenteACorsoRejectsMissingStudente() {
        Corso corso = corsoRepository.save(new Corso("Java", "Base", 8, null));

        assertThrows(
                ResourceNotFoundException.class,
                () -> iscrizioneService.iscriviStudenteACorso(99, corso.getId())
        );
    }

    @Test
    void iscriviStudenteACorsoRejectsMissingCorso() {
        Studente studente = studenteRepository.save(new Studente("Ada", "Lovelace", "ada@example.com", "S001"));

        assertThrows(
                ResourceNotFoundException.class,
                () -> iscrizioneService.iscriviStudenteACorso(studente.getId(), 99)
        );
    }

    @Test
    void iscriviStudenteACorsoRejectsDuplicateEnrollment() {
        Studente studente = studenteRepository.save(new Studente("Ada", "Lovelace", "ada@example.com", "S001"));
        Corso corso = corsoRepository.save(new Corso("Java", "Base", 8, null));
        iscrizioneService.iscriviStudenteACorso(studente.getId(), corso.getId());

        assertThrows(
                DuplicateResourceException.class,
                () -> iscrizioneService.iscriviStudenteACorso(studente.getId(), corso.getId())
        );
    }

    @Test
    void rimuoviIscrizioneRejectsMissingEnrollment() {
        Studente studente = studenteRepository.save(new Studente("Ada", "Lovelace", "ada@example.com", "S001"));
        Corso corso = corsoRepository.save(new Corso("Java", "Base", 8, null));

        assertThrows(
                ResourceNotFoundException.class,
                () -> iscrizioneService.rimuoviIscrizione(studente.getId(), corso.getId())
        );
    }

    private static class InMemoryIscrizioneRepository implements IscrizioneRepository {
        private final List<Iscrizione> iscrizioni = new ArrayList<>();

        @Override
        public Iscrizione save(Iscrizione iscrizione) {
            if (iscrizione.getId() == null) {
                iscrizione.setId(iscrizioni.size() + 1);
            }
            iscrizioni.add(iscrizione);
            return iscrizione;
        }

        @Override
        public Optional<Iscrizione> findByStudenteIdAndCorsoId(Integer studenteId, Integer corsoId) {
            return iscrizioni.stream()
                    .filter(iscrizione -> studenteId.equals(iscrizione.getStudente().getId()))
                    .filter(iscrizione -> corsoId.equals(iscrizione.getCorso().getId()))
                    .findFirst();
        }

        @Override
        public List<Corso> findCorsiByStudenteId(Integer studenteId) {
            return iscrizioni.stream()
                    .filter(iscrizione -> studenteId.equals(iscrizione.getStudente().getId()))
                    .map(Iscrizione::getCorso)
                    .toList();
        }

        @Override
        public void delete(Iscrizione iscrizione) {
            iscrizioni.remove(iscrizione);
        }

        @Override
        public boolean existsByStudenteIdAndCorsoId(Integer studenteId, Integer corsoId) {
            return findByStudenteIdAndCorsoId(studenteId, corsoId).isPresent();
        }

        @Override
        public Integer sumCfuByStudenteId(Integer studenteId) {
            return findCorsiByStudenteId(studenteId).stream()
                    .mapToInt(Corso::getCfu)
                    .sum();
        }

        private List<Iscrizione> findAll() {
            return iscrizioni;
        }
    }

    private static class InMemoryStudenteRepository implements StudenteRepository {
        private final List<Studente> studenti = new ArrayList<>();

        @Override
        public List<Studente> findAll() {
            return studenti;
        }

        @Override
        public PageResult<Studente> findAll(PageRequest pageRequest) {
            return new PageResult<>(studenti, pageRequest.page(), pageRequest.size(), studenti.size(), 1);
        }

        @Override
        public Optional<Studente> findById(Integer id) {
            return studenti.stream()
                    .filter(studente -> id.equals(studente.getId()))
                    .findFirst();
        }

        @Override
        public Studente save(Studente studente) {
            if (studente.getId() == null) {
                studente.setId(studenti.size() + 1);
            }
            studenti.add(studente);
            return studente;
        }

        @Override
        public void deleteById(Integer id) {
            studenti.removeIf(studente -> id.equals(studente.getId()));
        }

        @Override
        public boolean existsByEmail(String email) {
            return studenti.stream().anyMatch(studente -> email.equals(studente.getEmail()));
        }

        @Override
        public boolean existsByMatricola(String matricola) {
            return studenti.stream().anyMatch(studente -> matricola.equals(studente.getMatricola()));
        }
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
                    .filter(corso -> id.equals(corso.getId()))
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
            corsi.removeIf(corso -> id.equals(corso.getId()));
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
}
