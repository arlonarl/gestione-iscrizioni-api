package com.example.progettofinale.presentation.controller;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DocenteCorsoAndStudentiPaginationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createsDocenteAndOptionalCourseAssignment() throws Exception {
        Integer docenteId = createDocente("Grace", "Hopper", "grace@example.com");

        mockMvc.perform(post("/api/corsi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titolo": "Java",
                                  "descrizione": "Fondamenti",
                                  "cfu": 8,
                                  "docenteId": %d
                                }
                                """.formatted(docenteId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.docente.id").value(docenteId))
                .andExpect(jsonPath("$.docente.email").value("grace@example.com"));

        mockMvc.perform(post("/api/corsi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titolo": "SQL",
                                  "descrizione": "Database",
                                  "cfu": 6
                                }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.docente").isEmpty());
    }

    @Test
    void paginatesStudentList() throws Exception {
        createStudente("Ada", "Lovelace", "ada@example.com", "S001");
        createStudente("Alan", "Turing", "alan@example.com", "S002");
        createStudente("Katherine", "Johnson", "katherine@example.com", "S003");

        mockMvc.perform(get("/api/studenti")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.totalPages").value(2));
    }

    private Integer createDocente(String nome, String cognome, String email) throws Exception {
        String response = mockMvc.perform(post("/api/docenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "%s",
                                  "cognome": "%s",
                                  "email": "%s",
                                  "settore": "Informatica"
                                }
                                """.formatted(nome, cognome, email)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        return json.get("id").asInt();
    }

    private void createStudente(String nome, String cognome, String email, String matricola) throws Exception {
        mockMvc.perform(post("/api/studenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "%s",
                                  "cognome": "%s",
                                  "email": "%s",
                                  "matricola": "%s"
                                }
                                """.formatted(nome, cognome, email, matricola)))
                .andExpect(status().isCreated());
    }
}
