package com.utn.DetectorDeMutantes.dto;

import com.utn.DetectorDeMutantes.validation.ValidDnaSequence;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO para ingresar un ADN a analizar") //para Swagger
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DnaRequest {
    @Schema(
            description = "Secuencia de ADN representada como matriz cuadrada NxN",
            example = "[\"ATGCGA\", \"CAGTGC\", \"TTATGT\", \"AGAAGG\", \"CCCCTA\", \"TCACTG\"]",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    @NotEmpty
    @ValidDnaSequence
    private String[] dna;
}


