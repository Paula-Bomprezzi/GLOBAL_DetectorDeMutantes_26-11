package com.utn.DetectorDeMutantes.service;

import com.utn.DetectorDeMutantes.dto.StatsResponse;
import com.utn.DetectorDeMutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private StatsService statsService;

    @BeforeEach
    void setUp() {
        // Setup común si es necesario
    }

    @Test
    @DisplayName("Ratio normal - mutantes/humanos")
    void testGetStats_NormalRatio_ShouldCalculateCorrectly() {
        // Arrange
        long countMutants = 40L;
        long countHumans = 100L;
        double expectedRatio = 0.4; // 40/100

        when(repository.countByIsMutant(true)).thenReturn(countMutants);
        when(repository.countByIsMutant(false)).thenReturn(countHumans);

        // Act
        StatsResponse result = statsService.getStats();

        // Assert
        assertNotNull(result);
        assertEquals(countMutants, result.getCountMutantDna());
        assertEquals(countHumans, result.getCountHumanDna());
        assertEquals(expectedRatio, result.getRatio(), 0.001);
        verify(repository, times(1)).countByIsMutant(true);
        verify(repository, times(1)).countByIsMutant(false);
    }

    @Test
    @DisplayName("Sin humanos - caso especial")
    void testGetStats_NoHumans_ShouldReturnOnlyMutants() {
        // Arrange
        long countMutants = 50L;
        long countHumans = 0L;

        when(repository.countByIsMutant(true)).thenReturn(countMutants);
        when(repository.countByIsMutant(false)).thenReturn(countHumans);

        // Act
        StatsResponse result = statsService.getStats();

        // Assert
        assertNotNull(result);
        assertEquals(countMutants, result.getCountMutantDna());
        assertEquals(0, result.getCountHumanDna());
        // Cuando no hay humanos, el ratio no se establece (debería ser 0.0 por defecto)
        verify(repository, times(1)).countByIsMutant(true);
        verify(repository, times(1)).countByIsMutant(false);
    }

    @Test
    @DisplayName("Sin mutantes")
    void testGetStats_NoMutants_ShouldReturnZeroRatio() {
        // Arrange
        long countMutants = 0L;
        long countHumans = 100L;
        double expectedRatio = 0.0; // 0/100

        when(repository.countByIsMutant(true)).thenReturn(countMutants);
        when(repository.countByIsMutant(false)).thenReturn(countHumans);

        // Act
        StatsResponse result = statsService.getStats();

        // Assert
        assertNotNull(result);
        assertEquals(countMutants, result.getCountMutantDna());
        assertEquals(countHumans, result.getCountHumanDna());
        assertEquals(expectedRatio, result.getRatio(), 0.001);
        verify(repository, times(1)).countByIsMutant(true);
        verify(repository, times(1)).countByIsMutant(false);
    }

    @Test
    @DisplayName("Sin registros")
    void testGetStats_NoRecords_ShouldReturnZeros() {
        // Arrange
        long countMutants = 0L;
        long countHumans = 0L;

        when(repository.countByIsMutant(true)).thenReturn(countMutants);
        when(repository.countByIsMutant(false)).thenReturn(countHumans);

        // Act
        StatsResponse result = statsService.getStats();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getCountMutantDna());
        assertEquals(0, result.getCountHumanDna());
        // Cuando no hay humanos, no se establece ratio
        verify(repository, times(1)).countByIsMutant(true);
        verify(repository, times(1)).countByIsMutant(false);
    }

    @Test
    @DisplayName("Verificar cálculo correcto del ratio")
    void testGetStats_RatioCalculation_ShouldBeCorrect() {
        // Arrange - Caso con ratio > 1
        long countMutants = 150L;
        long countHumans = 100L;
        double expectedRatio = 1.5; // 150/100

        when(repository.countByIsMutant(true)).thenReturn(countMutants);
        when(repository.countByIsMutant(false)).thenReturn(countHumans);

        // Act
        StatsResponse result = statsService.getStats();

        // Assert
        assertNotNull(result);
        assertEquals(expectedRatio, result.getRatio(), 0.001);
    }

    @Test
    @DisplayName("Ratio con valores grandes")
    void testGetStats_LargeValues_ShouldCalculateCorrectly() {
        // Arrange
        long countMutants = 1000L;
        long countHumans = 5000L;
        double expectedRatio = 0.2; // 1000/5000

        when(repository.countByIsMutant(true)).thenReturn(countMutants);
        when(repository.countByIsMutant(false)).thenReturn(countHumans);

        // Act
        StatsResponse result = statsService.getStats();

        // Assert
        assertNotNull(result);
        assertEquals(countMutants, result.getCountMutantDna());
        assertEquals(countHumans, result.getCountHumanDna());
        assertEquals(expectedRatio, result.getRatio(), 0.001);
    }
}
