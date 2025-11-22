package com.utn.DetectorDeMutantes.service;

import com.utn.DetectorDeMutantes.dto.StatsResponse;
import com.utn.DetectorDeMutantes.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StatsService {
    private final DnaRecordRepository repo;



    public StatsResponse getStats() {
        Long m = repo.countByIsMutant(true);
        Long nm = repo.countByIsMutant(false);
        
        // Caso especial: No hay datos en el sistema
        if (m == 0 && nm == 0) {
            System.out.println("=====================================STATS=====================================");
            System.out.println("No hay datos en el sistema aún.");
            return StatsResponse.builder()
                    .countMutantDna(0)
                    .countHumanDna(0)
                    .ratio(0.0)
                    .build();
        }
        
        // Caso: Solo hay mutantes (sin humanos)
        if (nm == 0) {
            System.out.println("=====================================STATS=====================================");
            System.out.println("Solo hay mutantes en el sistema!, hay " + m + " para ser exactos.");
            return StatsResponse.builder()
                    .countMutantDna(m)
                    .countHumanDna(0)
                    .ratio(0.0)
                    .build();
        }
        
        // Caso normal: Hay mutantes y humanos
        System.out.println("=====================================STATS=====================================");
        System.out.println(" Contamos con un total de " + m + " mutantes en el sistema");
        System.out.println(" Contamos con un total de " + nm + " humanos en el sistema");
        double ratio = (double) m / nm;
        System.out.println(" El porcentaje de mutantes sobre humanos es de " + ratio + "%");
        return StatsResponse.builder()
                .countHumanDna(nm)
                .countMutantDna(m)
                .ratio(ratio)
                .build();
    }
}


