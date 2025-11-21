package com.utn.DetectorDeMutantes.controller;
import com.utn.DetectorDeMutantes.dto.DnaRequest;
import com.utn.DetectorDeMutantes.dto.StatsResponse;
import com.utn.DetectorDeMutantes.service.MutantService;
import com.utn.DetectorDeMutantes.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "Mutant Detector", description = "API para detectar mutantes analizando secuencias de ADN")
public class MutantController {

    //atributo
    private final StatsService  statsService;
    private final MutantService mutantService;



    //PARA EL SWAGGER
    @Operation(
            summary = "Verificar si un ADN es mutante",
            description = "Recibe una secuencia de ADN y determina si es mutante. " +
                    "Un humano es mutante si tiene más de una secuencia de 4 letras iguales " +
                    "en horizontal, vertical o diagonal."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Es mutante - El ADN contiene más de una secuencia de 4 letras iguales"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No es mutante - El ADN tiene 0 o 1 secuencia de 4 letras iguales"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "DNA inválido - El ADN no cumple con las validaciones (debe ser matriz cuadrada NxN, mínimo 4x4, solo caracteres A/T/C/G)"
            )
    })

//CARGAR UN ADN
    @PostMapping("/mutant")
    public ResponseEntity<?> checkMutant(@RequestBody @Valid DnaRequest request) {
        // Llamar al servicio para analizar el DNA
        boolean isMutant = mutantService.analyzeDna(request.getDna());
        
        // Según CONSIGNAS:
        // - 200 OK si es mutante
        // - 403 Forbidden si NO es mutante
        if (isMutant) {
            return ResponseEntity.ok().build();  // 200 OK - Es mutante
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // 403 Forbidden - No es mutante
        }
    }


    @Operation(
            summary = "Obtener estadísticas de ADNs analizados",
            description = "Retorna las estadísticas de todos los ADNs analizados: cantidad de mutantes, humanos y el ratio entre ellos."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Estadísticas obtenidas correctamente"
    )
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = statsService.getStats();
        return ResponseEntity.ok(stats);
    }



   

}


