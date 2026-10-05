package com.example.progettofinale.presentation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RestApiCrudEdIscrizioneIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void managesStudenteCrudAndDuplicateValidation() throws Exception {
        Integer studenteId = createStudente("Maria", "Montessori", "maria.crud@example.com", "CRUD-S001");

        mockMvc.perform(get("/api/studenti/{id}", studenteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("maria.crud@example.com"))
                .andExpect(jsonPath("$.matricola").value("CRUD-S001"));

        mockMvc.perform(put("/api/studenti/{id}", studenteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Maria",
                                  "cognome": "Rossi",
                                  "email": "maria.rossi.crud@example.com",
                                  "matricola": "CRUD-S001"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cognome").value("Rossi"))
                .andExpect(jsonPath("$.email").value("maria.rossi.crud@example.com"));

        mockMvc.perform(post("/api/studenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Duplicato",
                                  "cognome": "Email",
                                  "email": "maria.rossi.crud@example.com",
                                  "matricola": "CRUD-S002"
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email gia' utilizzata"));

        mockMvc.perform(delete("/api/studenti/{id}", studenteId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/studenti/{id}", studenteId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Studente non trovato"));
    }

    @Test
    void managesDocenteCrudAndRequestValidation() throws Exception {
        Integer docenteId = createDocente("Edsger", "Dijkstra", "edsger.crud@example.com", "Algoritmi");

        mockMvc.perform(get("/api/docenti/{id}", docenteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.settore").value("Algoritmi"));

        mockMvc.perform(put("/api/docenti/{id}", docenteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Edsger",
                                  "cognome": "Dijkstra",
                                  "email": "edsger.updated@example.com",
                                  "settore": "Computer science"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("edsger.updated@example.com"))
                .andExpect(jsonPath("$.settore").value("Computer science"));

        mockMvc.perform(post("/api/docenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "",
                                  "cognome": "NoEmail",
                                  "email": "non-valida",
                                  "settore": "Test"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Dati non validi"))
                .andExpect(jsonPath("$.errors.nome").value("Il nome e' obbligatorio"))
                .andExpect(jsonPath("$.errors.email").value("Formato email non valido"));
    }

    @Test
    void managesCourseUpdateDeleteAndMissingDocente() throws Exception {
        Integer corsoId = createCorso("Crud Corso", "Versione iniziale", 5, null);
        Integer docenteId = createDocente("Barbara", "Liskov", "barbara.course@example.com", "Sistemi");

        mockMvc.perform(put("/api/corsi/{id}", corsoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titolo": "Crud Corso 2",
                                  "descrizione": "Versione aggiornata",
                                  "cfu": 9,
                                  "docenteId": %d
                                }
                                """.formatted(docenteId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titolo").value("Crud Corso 2"))
                .andExpect(jsonPath("$.cfu").value(9))
                .andExpect(jsonPath("$.docente.id").value(docenteId))
                .andExpect(jsonPath("$.docente.email").value("barbara.course@example.com"));

        mockMvc.perform(post("/api/corsi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titolo": "Docente assente",
                                  "descrizione": "Non salvabile",
                                  "cfu": 6,
                                  "docenteId": 999999
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Docente non trovato"));

        mockMvc.perform(delete("/api/corsi/{id}", corsoId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/corsi/{id}", corsoId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Corso non trovato"));
    }

    @Test
    void returnsCompactErrorsForInvalidHttpRequests() throws Exception {
        mockMvc.perform(get("/api/corsi/non-numerico"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Parametro non valido"))
                .andExpect(jsonPath("$.errors.id").value("Valore non corretto"))
                .andExpect(jsonPath("$.trace").doesNotExist())
                .andExpect(jsonPath("$.exception").doesNotExist());

        mockMvc.perform(post("/api/corsi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Richiesta non leggibile"))
                .andExpect(jsonPath("$.trace").doesNotExist())
                .andExpect(jsonPath("$.exception").doesNotExist());

        mockMvc.perform(post("/api/docenti")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("contenuto non json"))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.message").value("Formato della richiesta non supportato"))
                .andExpect(jsonPath("$.trace").doesNotExist())
                .andExpect(jsonPath("$.exception").doesNotExist());

        mockMvc.perform(delete("/api/docenti"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value("Metodo HTTP non supportato"))
                .andExpect(jsonPath("$.trace").doesNotExist())
                .andExpect(jsonPath("$.exception").doesNotExist());
    }

    @Test
    void enrollsStudentListsCoursesCalculatesCfuAndPreventsDuplicates() throws Exception {
        Integer studenteId = createStudente("Tim", "Berners-Lee", "tim.enroll@example.com", "ENR-S001");
        Integer webCourseId = createCorso("Web Test", "HTTP", 6, null);
        Integer dbCourseId = createCorso("DB Test", "SQL", 9, null);

        mockMvc.perform(post("/api/studenti/{id}/corsi/{corsoId}", studenteId, webCourseId))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/studenti/{id}/corsi/{corsoId}", studenteId, dbCourseId))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/studenti/{id}/corsi/{corsoId}", studenteId, webCourseId))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Studente gia' iscritto al corso"));

        mockMvc.perform(get("/api/studenti/{id}/corsi", studenteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studente.id").value(studenteId))
                .andExpect(jsonPath("$.corsi", hasSize(2)));

        mockMvc.perform(get("/api/studenti/{id}/cfu-totali", studenteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studenteId").value(studenteId))
                .andExpect(jsonPath("$.cfuTotali").value(15));

        mockMvc.perform(delete("/api/studenti/{id}/corsi/{corsoId}", studenteId, webCourseId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/studenti/{id}/cfu-totali", studenteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cfuTotali").value(9));
    }

    @Test
    void returnsCourseRankingByEnrollmentCount() throws Exception {
        Integer firstStudentId = createStudente("Linus", "Torvalds", "linus.rank@example.com", "RANK-S001");
        Integer secondStudentId = createStudente("Ken", "Thompson", "ken.rank@example.com", "RANK-S002");
        Integer popularCourseId = createCorso("Rank Popular", "Due iscritti", 4, null);
        Integer singleCourseId = createCorso("Rank Single", "Un iscritto", 3, null);

        mockMvc.perform(post("/api/studenti/{id}/corsi/{corsoId}", firstStudentId, popularCourseId))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/studenti/{id}/corsi/{corsoId}", secondStudentId, popularCourseId))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/studenti/{id}/corsi/{corsoId}", firstStudentId, singleCourseId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/corsi/classifica-iscritti"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].id").value(popularCourseId))
                .andExpect(jsonPath("$[0].corso").doesNotExist())
                .andExpect(jsonPath("$[0].docente").doesNotExist())
                .andExpect(jsonPath("$[0].numeroIscritti").value(2));
    }

    private Integer createStudente(String nome, String cognome, String email, String matricola) throws Exception {
        String response = mockMvc.perform(post("/api/studenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "%s",
                                  "cognome": "%s",
                                  "email": "%s",
                                  "matricola": "%s"
                                }
                                """.formatted(nome, cognome, email, matricola)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.startsWith("/api/studenti/")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return readId(response);
    }

    private Integer createCorso(String titolo, String descrizione, Integer cfu, Integer docenteId) throws Exception {
        String docenteJson = docenteId == null ? "null" : docenteId.toString();
        String response = mockMvc.perform(post("/api/corsi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titolo": "%s",
                                  "descrizione": "%s",
                                  "cfu": %d,
                                  "docenteId": %s
                                }
                                """.formatted(titolo, descrizione, cfu, docenteJson)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.startsWith("/api/corsi/")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return readId(response);
    }

    private Integer createDocente(String nome, String cognome, String email, String settore) throws Exception {
        String response = mockMvc.perform(post("/api/docenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "%s",
                                  "cognome": "%s",
                                  "email": "%s",
                                  "settore": "%s"
                                }
                                """.formatted(nome, cognome, email, settore)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.startsWith("/api/docenti/")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return readId(response);
    }

    private Integer readId(String response) throws Exception {
        JsonNode json = objectMapper.readTree(response);
        return json.get("id").asInt();
    }
}
