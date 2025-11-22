package com.utn.DetectorDeMutantes.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {

    private static MutantDetector detector;

    @BeforeAll
    static void setUp() {
        detector = new MutantDetector();
    }

    // ==================== CASOS MUTANTES (debe retornar true) ====================

    @Test
    @DisplayName("Mutante con secuencia horizontal y diagonal")
    public void testMutantWithHorizontalAndDiagonalSequences() {
        // Ejemplo del enunciado: horizontal CCCC y diagonal AAAA
        assertTrue(detector.isMutant(new String[] {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGAAGG",  // Diagonal: A-A-A-A
                "CCCCTA",  // Horizontal: C-C-C-C
                "TCACTG"
        }));
    }

    @Test
    @DisplayName("Mutante con secuencias verticales")
    public void testMutantWithVerticalSequences() {
        // Dos secuencias verticales: columna 0 (A-A-A-A) y columna 2 (G-G-G-G)
        assertTrue(detector.isMutant(new String[] {
                "ATGCGA",
                "ATGCGA",
                "ATGCGA",
                "ATGCGA",
                "CAGTGC",
                "TCACTG"
        }));
    }

    @Test
    @DisplayName("Mutante con múltiples secuencias horizontales")
    public void testMutantWithMultipleHorizontalSequences() {
        // Dos secuencias horizontales
        assertTrue(detector.isMutant(new String[] {
                "AAAAAA",
                "CAGTGC",
                "TTTTTT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        }));
    }

    @Test
    @DisplayName("Mutante con diagonales ascendentes y descendentes")
    public void testMutantWithBothDiagonals() {
        assertTrue(detector.isMutant(new String[] {
                "ATGCGA",
                "GAGTGC",
                "GGATCT",
                "AGGAGG",
                "CCGCTA",
                "TCAGTG"
        }));
    }

    @Test
    @DisplayName("Mutante con matriz grande 10x10")
    public void testMutantWithLargeDna() {
        // Matriz 10x10 con dos secuencias horizontales (fila 0: AAAA, fila 2: TTTT)
        assertTrue(detector.isMutant(new String[] {
                "AAAAGATGCG",
                "CAGTGCCAGT",
                "TTTTGTATGT",
                "AGAAGGAGAA",
                "GCCCTACCCC",
                "TCACTGTCAC",
                "ATGCGATGCG",
                "CAGTGCCAGT",
                "TTATGTATGT",
                "AGAAGGAGAA"
        }));
    }

    @Test
    @DisplayName("Mutante con todos los caracteres iguales")
    public void testMutantAllSameCharacter() {
        // Matriz 6x6 con todas A
        assertTrue(detector.isMutant(new String[] {
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA"
        }));
    }

    @Test
    @DisplayName("Mutante con diagonal en esquina")
    public void testMutantDiagonalInCorner() {
        //Comparten la A inicial
        assertTrue(detector.isMutant(new String[] {
                "AAAATA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCGCTA",
                "TCACTG"
        }));
    }

    // ==================== CASOS HUMANOS (debe retornar false) ====================

    @Test
    @DisplayName("Humano con solo una secuencia")
    public void testOneSequence() {
        // Solo una secuencia horizontal (TTTT en fila 2) - ejemplo de CONSIGNAS
        assertFalse(detector.isMutant(new String[] {
                "ATGCGA",
                "CTTTTC",
                "TTATTT",  // Solo una secuencia: T-T-T-T
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        }));
    }

    @Test
    @DisplayName("Humano sin secuencias")
    public void testNoSequence() {
        // Sin secuencias de 4 iguales - ejemplo de CONSIGNAS
        assertFalse(detector.isMutant(new String[] {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        }));
    }

    @Test
    @DisplayName("Humano con matriz pequeña 4x4 con una secuencia")
    public void testHumanWithSmallDna() {
        // Matriz 4x4 con una secuencia
        assertFalse(detector.isMutant(new String[] {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAA"
        }));
    }

    // ==================== VALIDACIONES (debe retornar false) ====================

    @Test
    @DisplayName("Validación: DNA null")
    public void testNullDna() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    @DisplayName("Validación: Array vacío")
    public void testEmptyDna() {
        assertFalse(detector.isMutant(new String[] {}));
    }

    @Test
    @DisplayName("Validación: Matriz no cuadrada")
    public void testNonSquareMatrix() {
        // Matriz 4x5 (no cuadrada)
        assertFalse(detector.isMutant(new String[] {
                "ATGCG",
                "CAGTG",
                "TTATG",
                "AGAAG"
        }));
    }

    @Test
    @DisplayName("Validación: Caracteres inválidos")
    public void testInvalidDna() {
        // Contiene 'X' que no es válido
        assertFalse(detector.isMutant(new String[] {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCXTA",
                "TCACTG"
        }));
    }

    @Test
    @DisplayName("Validación: Fila null")
    public void testNullRow() {
        // Una fila es null
        assertFalse(detector.isMutant(new String[] {
                "ATGCGA",
                null,
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        }));
    }

    @Test
    @DisplayName("Validación: Matriz muy pequeña")
    public void testTooSmallMatrix() {
        // Matriz 3x3 (menor que 4x4)
        assertFalse(detector.isMutant(new String[] {
                "ATG",
                "CAG",
                "TTA"
        }));
    }

    // ==================== EDGE CASES ====================

    @Test
    @DisplayName("Edge case: Secuencia de longitud 5 no debe contar")
    public void testSequenceLongerThanFourShouldNotCount() {
        // Tiene 5 A's consecutivos pero solo cuenta como 1 secuencia
        assertFalse(detector.isMutant(new String[] {
                "AAAAAG",
                "CAGTGC",
                "TTCTGT",
                "AGAAGG",
                "GCCCTA",
                "TCACTG"
        }));
    }

    @Test
    @DisplayName("Mutante horizontal")
    public void testHorizontalMutant() {
        assertTrue(detector.isMutant(new String[] {
                "AAAAAA",
                "CAGTGC",
                "TTTTTT",
                "AGAAGG",
                "GCCCTA",
                "TCACTG"
        }));
    }

    @Test
    @DisplayName("Mutante diagona")
    public void testDiagonalMutant() {
        // Dos secuencias diagonales: descendente desde (0,0) AAAA y descendente desde (1,0) GGGG
        assertTrue(detector.isMutant(new String[] {
                "ATGCGA",
                "GAGTGC",
                "GGATGT",
                "AGTAGG",
                "CCGCTA",
                "TCAGTG"
        }));
    }

    // ==================== TESTS DE PERFORMANCE (RÚBRICAS 1.2) ====================

    /**
     * Genera una matriz de ADN de tamaño NxN con caracteres aleatorios válidos
     */
    private String[] generateDna(int size) {
        String[] dna = new String[size];
        char[] bases = {'A', 'T', 'C', 'G'};
        java.util.Random random = new java.util.Random();
        
        for (int i = 0; i < size; i++) {
            StringBuilder row = new StringBuilder(size);
            for (int j = 0; j < size; j++) {
                row.append(bases[random.nextInt(bases.length)]);
            }
            dna[i] = row.toString();
        }
        return dna;
    }

    /**
     * Genera una matriz de ADN de tamaño NxN que es mutante (para pruebas de performance)
     */
    private String[] generateMutantDna(int size) {
        String[] dna = new String[size];
        char[] bases = {'A', 'T', 'C', 'G'};
        java.util.Random random = new java.util.Random();
        
        // Primera fila: secuencia horizontal de AAAA
        StringBuilder firstRow = new StringBuilder(size);
        firstRow.append("AAAA");
        for (int j = 4; j < size; j++) {
            firstRow.append(bases[random.nextInt(bases.length)]);
        }
        dna[0] = firstRow.toString();
        
        // Segunda fila: diagonal desde (0,0) con AAAA
        StringBuilder secondRow = new StringBuilder(size);
        secondRow.append("A");
        for (int j = 1; j < size && j < 4; j++) {
            secondRow.append("A");
        }
        for (int j = 4; j < size; j++) {
            secondRow.append(bases[random.nextInt(bases.length)]);
        }
        if (size > 1) {
            dna[1] = secondRow.toString();
        }
        
        // Resto de filas aleatorias
        for (int i = 2; i < size; i++) {
            StringBuilder row = new StringBuilder(size);
            for (int j = 0; j < size; j++) {
                row.append(bases[random.nextInt(bases.length)]);
            }
            dna[i] = row.toString();
        }
        
        return dna;
    }

    @Test
    @DisplayName("Performance: Matriz 6x6 debe ser ≤ 5ms")
    public void testPerformance_6x6() {
        // Arrange - Generar matriz FUERA de la medición
        String[] dna = generateDna(6);
        
        // Warmup - Ejecutar una vez para calentar la JVM
        detector.isMutant(dna);
        
        // Act - Medir SOLO el tiempo de isMutant() con múltiples ejecuciones
        int iterations = 1000; // Múltiples ejecuciones para mayor precisión
        long totalNanos = 0;
        
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            detector.isMutant(dna);
            long end = System.nanoTime();
            totalNanos += (end - start);
        }
        
        // Calcular promedio
        long avgNanos = totalNanos / iterations;
        double avgMs = avgNanos / 1_000_000.0;
        
        // Assert
        assertTrue(avgMs <= 5, 
            "6x6 debe ser ≤ 5ms (aceptable), promedio fue: " + String.format("%.3f", avgMs) + "ms");
        
        // Información adicional con mayor precisión
        System.out.println("Performance 6x6: " + String.format("%.3f", avgMs) + "ms (promedio de " + iterations + " ejecuciones, límite: 5ms)");
    }

    @Test
    @DisplayName("Performance: Matriz 100x100 debe ser ≤ 100ms")
    public void testPerformance_100x100() {
        // Arrange - Generar matriz FUERA de la medición
        String[] dna = generateDna(100);
        
        // Warmup - Ejecutar una vez para calentar la JVM
        detector.isMutant(dna);
        
        // Act - Medir SOLO el tiempo de isMutant() con múltiples ejecuciones
        int iterations = 100; // Menos iteraciones para matrices grandes
        long totalNanos = 0;
        
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            detector.isMutant(dna);
            long end = System.nanoTime();
            totalNanos += (end - start);
        }
        
        // Calcular promedio
        long avgNanos = totalNanos / iterations;
        double avgMs = avgNanos / 1_000_000.0;
        
        // Assert
        assertTrue(avgMs <= 100, 
            "100x100 debe ser ≤ 100ms (aceptable), promedio fue: " + String.format("%.3f", avgMs) + "ms");
        
        // Información adicional con mayor precisión
        System.out.println("Performance 100x100: " + String.format("%.3f", avgMs) + "ms (promedio de " + iterations + " ejecuciones, límite: 100ms)");
    }

    @Test
    @DisplayName("Performance: Matriz 1000x1000 debe ser ≤ 5000ms")
    public void testPerformance_1000x1000() {
        // Arrange - Generar matriz FUERA de la medición
        String[] dna = generateDna(1000);
        
        // Warmup - Ejecutar una vez para calentar la JVM
        detector.isMutant(dna);
        
        // Act - Medir SOLO el tiempo de isMutant() con múltiples ejecuciones
        int iterations = 10; // Pocas iteraciones para matrices muy grandes
        long totalNanos = 0;
        
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            detector.isMutant(dna);
            long end = System.nanoTime();
            totalNanos += (end - start);
        }
        
        // Calcular promedio
        long avgNanos = totalNanos / iterations;
        double avgMs = avgNanos / 1_000_000.0;
        
        // Assert
        assertTrue(avgMs <= 5000, 
            "1000x1000 debe ser ≤ 5000ms (aceptable), promedio fue: " + String.format("%.3f", avgMs) + "ms");
        
        // Información adicional con mayor precisión
        System.out.println("Performance 1000x1000: " + String.format("%.3f", avgMs) + "ms (promedio de " + iterations + " ejecuciones, límite: 5000ms)");
    }

    @Test
    @DisplayName("Performance: Matriz 6x6 con early termination debe ser rápido")
    public void testPerformance_6x6_WithEarlyTermination() {
        // Arrange - Matriz mutante (encuentra 2 secuencias rápido) FUERA de la medición
        String[] dna = generateMutantDna(6);
        
        // Warmup - Ejecutar una vez para calentar la JVM
        boolean warmupResult = detector.isMutant(dna);
        assertTrue(warmupResult, "Debe ser mutante");
        
        // Act - Medir SOLO el tiempo de isMutant() con múltiples ejecuciones
        int iterations = 1000;
        long totalNanos = 0;
        
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            detector.isMutant(dna);
            long end = System.nanoTime();
            totalNanos += (end - start);
        }
        
        // Calcular promedio
        long avgNanos = totalNanos / iterations;
        double avgMs = avgNanos / 1_000_000.0;
        
        // Assert
        assertTrue(avgMs <= 5, 
            "6x6 con early termination debe ser ≤ 5ms, promedio fue: " + String.format("%.3f", avgMs) + "ms");
        
        System.out.println("Performance 6x6 (early termination): " + String.format("%.3f", avgMs) + "ms (promedio de " + iterations + " ejecuciones)");
    }

}


