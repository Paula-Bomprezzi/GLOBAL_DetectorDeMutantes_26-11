package com.utn.DetectorDeMutantes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Lombok
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatsResponse {
    @Schema(description = "Cantidad de ADNs mutantes detectados")  // Swagger
    @JsonProperty("count_mutant_dna")  // Para que el JSON tenga guión bajo
    private long countMutantDna;

    @Schema(description = "Cantidad de ADNs humanos detectados")
    @JsonProperty("count_human_dna")
    private long countHumanDna;

    @Schema(description = "Ratio de mutantes sobre humanos")
    private double ratio;
}