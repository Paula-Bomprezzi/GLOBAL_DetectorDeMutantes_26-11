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
        Long nm =repo.countByIsMutant(false);
        if(nm==0){
            return StatsResponse.builder()
                    .countMutantDna(m)
                    .build();
        }else {
            System.out.println("=====================================STATS=====================================");
            System.out.println(" Contamos con un total de" + m + " mutantes en el sistema");
            System.out.println(" Contamos con un total de" + nm + " humanos en el sistema");
            double ratio = (double) m/nm;
            System.out.println(" El porcentaje de mutantes sobre humanos es de " + ratio + "%");
            return  StatsResponse.builder()
                    .countHumanDna(nm)
                    .countMutantDna(m)
                    .ratio(ratio)
                    .build();
        }

    };
}


