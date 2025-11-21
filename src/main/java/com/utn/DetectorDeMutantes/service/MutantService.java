package com.utn.DetectorDeMutantes.service;

import com.utn.DetectorDeMutantes.entity.DnaRecord;
import com.utn.DetectorDeMutantes.exception.DnaHashCalculationException;
import com.utn.DetectorDeMutantes.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MutantService {

    private final DnaRecordRepository repo;
    private final MutantDetector mutantDetector;
    private final MapperService mapper;



    private String calculateDnaHash(String[] dna) {
        try {
            // 1. Concatenar todas las filas del DNA sin separadores
            StringBuilder sb = new StringBuilder();
            for (String row : dna) {
                sb.append(row);
            }
            String concatenatedDna = sb.toString();

            // 2. Obtener instancia de MessageDigest con SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 3. Calcular el hash (devuelve bytes)
            byte[] hashBytes = digest.digest(concatenatedDna.getBytes());

            // 4. Convertir bytes a hexadecimal (64 caracteres)
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0'); // Asegurar 2 dígitos por byte
                }
                hexString.append(hex);
            }

            return hexString.toString(); // Retorna String de 64 caracteres hexadecimales

        } catch (NoSuchAlgorithmException e) {
            throw new DnaHashCalculationException(
                    "Error al calcular hash SHA-256 del DNA", e);
        }
    }

    public boolean analyzeDna( String[] dna){
        String hash = calculateDnaHash(dna);
        Optional<DnaRecord> dnaBuscado = repo.findByDnaHash(hash);
        if(dnaBuscado.isPresent()){
            boolean isMutant = dnaBuscado.get().getIsMutant();
            System.out.println("=========================RESULTADOS=========================");
            System.out.println("El ADN ingresado dice que el sujeto...");
            System.out.println( isMutant ? "ES MUTANTE!!" : "No es mutante");
            return isMutant;
    } else{
            System.out.println("ANALIZANDO.......");
            boolean isMutant = mutantDetector.isMutant(dna);
            DnaRecord dnaAnalizado = DnaRecord.builder()
                    .dnaHash(hash)
                    .isMutant(isMutant)
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .build();
            repo.save(dnaAnalizado);
            System.out.println("ANÁLISIS CONCLUIDO");
            System.out.println("=========================RESULTADOS=========================");
            System.out.println("El ADN ingresado dice que el sujeto...");
            System.out.println( isMutant ? "ES MUTANTE!!" : "No es mutante");
            return isMutant;
        }
    };
}


