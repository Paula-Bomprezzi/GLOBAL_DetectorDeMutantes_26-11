package com.utn.DetectorDeMutantes.service;

import com.utn.DetectorDeMutantes.dto.StatsResponse;
import com.utn.DetectorDeMutantes.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatsService {
    private final DnaRecordRepository repo;



    public StatsResponse getStats() {
        Long m = repo.countByIsMutant(true);
        Long nm = repo.countByIsMutant(false);
        
        // Caso especial: No hay datos en el sistema
        if (m == 0 && nm == 0) {
            log.info("=====================================STATS=====================================");
            log.info("No hay datos en el sistema aún.");
            return StatsResponse.builder()
                    .countMutantDna(0)
                    .countHumanDna(0)
                    .ratio(0.0)
                    .build();
        }
        
        // Caso: Solo hay mutantes (sin humanos)
        if (nm == 0) {
            log.info("=====================================STATS=====================================");
            log.info("Solo hay mutantes en el sistema!, hay {} para ser exactos.", m);
            return StatsResponse.builder()
                    .countMutantDna(m)
                    .countHumanDna(0)
                    .ratio(0.0)
                    .build();
        }
        
        // Caso normal: Hay mutantes y humanos
        log.info("=====================================STATS=====================================");
        log.info(" Contamos con un total de {} mutantes en el sistema", m);
        log.info(" Contamos con un total de {} humanos en el sistema", nm);
        double ratio = (double) m / nm;
        log.info(" El porcentaje de mutantes sobre humanos es de {}%", ratio);
        return StatsResponse.builder()
                .countHumanDna(nm)
                .countMutantDna(m)
                .ratio(ratio)
                .build();
    }
}


