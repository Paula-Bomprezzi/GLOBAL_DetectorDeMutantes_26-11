package com.utn.DetectorDeMutantes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "DTO para responder a errores (para que todos los errores se vean iguales)")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @Schema(description = "Fecha y hora en que ocurrió el error", example = "2025-01-07T15:30:45.123")
    private LocalDateTime timestamp;
    
    @Schema(description = "Código de estado HTTP", example = "400")
    private int status;
    
    @Schema(description = "Tipo de error", example = "Bad Request")
    private String error;
    
    @Schema(description = "Mensaje descriptivo del error", example = "Invalid DNA sequence: must be a square NxN matrix")
    private String message;
    
    @Schema(description = "Ruta del endpoint donde ocurrió el error", example = "/mutant")
    private String path;

    // Constructor para facilitar la creación
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}


