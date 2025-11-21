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
                "TTATGT",
                "AGAAGG",  // Diagonal ↘: A-A-A-A
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
        // Diagonal descendente desde (0,0): A-A-A-A y diagonal descendente desde (1,0): G-G-G-G
        assertTrue(detector.isMutant(new String[] {
                "ATGCGA",
                "GAGTGC",
                "TGGTGT",
                "AGGGGG",
                "CCGCTA",
                "TCACTG"
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
        // Diagonal en esquina superior izquierda y otra secuencia
        assertTrue(detector.isMutant(new String[] {
                "AAAAAA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        }));
    }

    // ==================== CASOS HUMANOS (debe retornar false) ====================

    @Test
    @DisplayName("Humano con solo una secuencia")
    public void testNotMutantWithOnlyOneSequence() {
        // Solo una secuencia horizontal (TTTT en fila 2) - ejemplo de CONSIGNAS
        assertFalse(detector.isMutant(new String[] {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",  // Solo una secuencia: T-T-T-T
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        }));
    }

    @Test
    @DisplayName("Humano sin secuencias")
    public void testNotMutantWithNoSequences() {
        // Sin secuencias de 4 iguales - ejemplo de CONSIGNAS
        assertFalse(detector.isMutant(new String[] {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        }));
    }

    @Test
    @DisplayName("Humano con matriz pequeña 4x4 sin secuencias")
    public void testNotMutantSmallDna() {
        // Matriz 4x4 sin secuencias
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
    public void testNotMutantWithNullDna() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    @DisplayName("Validación: Array vacío")
    public void testNotMutantWithEmptyDna() {
        assertFalse(detector.isMutant(new String[] {}));
    }

    @Test
    @DisplayName("Validación: Matriz no cuadrada")
    public void testNotMutantWithNonSquareDna() {
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
    public void testNotMutantWithInvalidCharacters() {
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
    public void testNotMutantWithNullRow() {
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
    public void testNotMutantWithTooSmallDna() {
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
    public void testNotMutantWithSequenceLongerThanFour() {
        // Tiene 5 A's consecutivos pero solo cuenta como 1 secuencia
        // Necesitamos otra secuencia para que sea mutante
        assertFalse(detector.isMutant(new String[] {
                "AAAAA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "GCCCTA",
                "TCACTG"
        }));
    }

    @Test
    @DisplayName("Mutante horizontal - cumple patrón requerido")
    public void testHorizontalMutant() {
        // Dos secuencias horizontales para cumplir patrón test.*[Hh]orizontal.*[Mm]utant
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
    @DisplayName("Mutante diagonal - cumple patrón requerido")
    public void testDiagonalMutant() {
        // Dos secuencias diagonales: descendente desde (0,0) AAAA y descendente desde (1,0) GGGG
        assertTrue(detector.isMutant(new String[] {
                "ATGCGA",
                "GAGTGC",
                "TGGTGT",
                "AGGGGG",
                "CCGCTA",
                "TCACTG"
        }));
    }
}


