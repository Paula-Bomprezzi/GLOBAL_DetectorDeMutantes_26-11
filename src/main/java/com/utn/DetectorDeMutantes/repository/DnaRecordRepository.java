package com.utn.DetectorDeMutantes.repository;
import com.utn.DetectorDeMutantes.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    //Métodos que se van a autogenerar
    Optional<DnaRecord> findBydnaHash(String dnaHash);

    long countByisMutant(boolean isMutant);
}

