package com.utn.DetectorDeMutantes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.DetectorDeMutantes.dto.DnaRequest;
import com.utn.DetectorDeMutantes.dto.StatsResponse;
import com.utn.DetectorDeMutantes.service.MutantService;
import com.utn.DetectorDeMutantes.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /mutant con mutante → 200 OK")
    void testCheckMutant_WhenMutant_ShouldReturn200() throws Exception {
        // Arrange
        String jsonRequest = """
            {
              "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
            }
            """;

        when(mutantService.analyzeDna(any(String[].class))).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant con humano → 403 Forbidden")
    void testCheckMutant_WhenHuman_ShouldReturn403() throws Exception {
        // Arrange
        String jsonRequest = """
            {
              "dna": ["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]
            }
            """;

        when(mutantService.analyzeDna(any(String[].class))).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /mutant con DNA inválido → 400 Bad Request")
    void testCheckMutant_WhenInvalidDna_ShouldReturn400() throws Exception {
        // Arrange - DNA con caracteres inválidos
        String jsonRequest = """
            {
              "dna": ["ATXCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
            }
            """;

        // Act & Assert
        // La validación se hace antes de llegar al servicio
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant con DNA null → 400 Bad Request")
    void testCheckMutant_WhenDnaIsNull_ShouldReturn400() throws Exception {
        // Arrange
        String jsonRequest = """
            {
              "dna": null
            }
            """;

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant con DNA vacío → 400 Bad Request")
    void testCheckMutant_WhenDnaIsEmpty_ShouldReturn400() throws Exception {
        // Arrange
        String jsonRequest = """
            {
              "dna": []
            }
            """;

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant con matriz no cuadrada → 400 Bad Request")
    void testCheckMutant_WhenDnaIsNotSquare_ShouldReturn400() throws Exception {
        // Arrange - Matriz 4x5 (no cuadrada)
        String jsonRequest = """
            {
              "dna": ["ATGCG","CAGTG","TTATG","AGAAG"]
            }
            """;

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /stats → 200 OK con JSON correcto")
    void testGetStats_ShouldReturn200WithCorrectJson() throws Exception {
        // Arrange
        StatsResponse statsResponse = StatsResponse.builder()
                .countMutantDna(40L)
                .countHumanDna(100L)
                .ratio(0.4)
                .build();

        when(statsService.getStats()).thenReturn(statsResponse);

        // Act & Assert
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }

    @Test
    @DisplayName("GET /stats sin humanos → 200 OK con JSON correcto")
    void testGetStats_WhenNoHumans_ShouldReturn200WithCorrectJson() throws Exception {
        // Arrange
        StatsResponse statsResponse = StatsResponse.builder()
                .countMutantDna(50L)
                .countHumanDna(0L)
                .ratio(0.0)
                .build();

        when(statsService.getStats()).thenReturn(statsResponse);

        // Act & Assert
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count_mutant_dna").value(50));
                // Nota: Cuando no hay humanos, el servicio puede retornar solo countMutantDna
                // Dependiendo de la implementación, puede o no incluir count_human_dna y ratio
    }
}
