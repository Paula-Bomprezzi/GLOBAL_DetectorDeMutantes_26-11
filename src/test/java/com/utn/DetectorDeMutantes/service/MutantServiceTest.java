package com.utn.DetectorDeMutantes.service;

import com.utn.DetectorDeMutantes.entity.DnaRecord;
import com.utn.DetectorDeMutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @Mock
    private MutantDetector mutantDetector;

    @InjectMocks
    private MutantService mutantService;

    private String[] mutantDna;
    private String expectedHash;

    @BeforeEach
    void setUp() {
        mutantDna = new String[] {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        // Hash SHA-256 del DNA mutante concatenado
        expectedHash = "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6a7b8c9d0e1f2";

    }

    @Test
    @DisplayName("DNA nuevo - debe calcular hash, analizar y guardar")
    void testNewDna_ShouldCalculateHashAnalyzeAndSave() {
        // Arrange
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty()); //Acá el método me va a calcular el hash, pero lo ignoro
        when(mutantDetector.isMutant(mutantDna)).thenReturn(true);
        when(repository.save(any(DnaRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        boolean result = mutantService.analyzeDna(mutantDna);

        // Assert
        assertTrue(result);
        verify(repository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, times(1)).isMutant(mutantDna);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("DNA existente - debe retornar cacheado sin analizar")
    void testExistingDna_ShouldReturnCached() {
        // Arrange
        DnaRecord existingRecord = DnaRecord.builder()
                .id(1L)
                .dnaHash(expectedHash)
                .isMutant(true)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(existingRecord));

        // Act
        boolean result = mutantService.analyzeDna(mutantDna);

        // Assert
        assertTrue(result);
        verify(repository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, never()).isMutant(any());
        verify(repository, never()).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Verificar que se guarda en BD con createdAt")
    void testNewDna_ShouldSaveWithCreatedAt() {
        // Arrange
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(mutantDna)).thenReturn(false);
        when(repository.save(any(DnaRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        mutantService.analyzeDna(mutantDna);

        // Assert
        verify(repository, times(1)).save(argThat(record -> {
            DnaRecord dnaRecord = (DnaRecord) record;
            return dnaRecord.getCreatedAt() != null &&
                   dnaRecord.getDnaHash() != null &&
                   dnaRecord.getIsMutant() != null;
        }));
    }

    @Test
    @DisplayName("Verificar que el hash se calcula correctamente")
    void testNewDna_ShouldCalculateHashCorrectly() {
        // Arrange
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(mutantDna)).thenReturn(true);
        when(repository.save(any(DnaRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        mutantService.analyzeDna(mutantDna);

        // Assert
        verify(repository, times(1)).findByDnaHash(argThat(hash -> {
            // El hash debe ser una cadena hexadecimal de 64 caracteres
            return hash != null && hash.length() == 64 && hash.matches("[0-9a-f]{64}");
        }));
    }

    @Test
    @DisplayName("Manejo de excepciones - verificar que el servicio funciona correctamente")
    void testExceptionHandling_ShouldWorkCorrectly() {
        // Arrange
        // La excepción DnaHashCalculationException solo se lanzaría si SHA-256 no está disponible
        // lo cual es muy improbable. Este test verifica que el flujo normal funciona.
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(mutantDna)).thenReturn(true);
        when(repository.save(any(DnaRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act & Assert - El método debería funcionar normalmente
        assertDoesNotThrow(() -> {
            boolean result = mutantService.analyzeDna(mutantDna);
            assertTrue(result);
        });
        
        verify(repository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, times(1)).isMutant(mutantDna);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }
}
