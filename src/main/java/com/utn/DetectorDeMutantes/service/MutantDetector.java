package com.utn.DetectorDeMutantes.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Set;

@Slf4j
@Service
public class MutantDetector {

    // Constructor público sin argumentos
    public MutantDetector() {
    }

    //Defino cuál es el tamaño mínimo
    private static final int SEQUENCE_LENGTH = 4;
    //defino qué caracteres acepto
    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');


    //MÉTODO
    public boolean isMutant(String[] dna) {

        //===============================SEGUNDA CAPA DE VALIDACIÓN (primera en el DTO)=======================
        // Validación inicial
        if (dna == null || dna.length == 0) {
            return false;
        }

        final int n = dna.length;
        if (n < SEQUENCE_LENGTH) {
            return false;
        }

        // Validar matriz cuadrada y caracteres válidos
        for (String row : dna) {
            if (row == null || row.length() != n) {
                return false;
            }

            for (char c : row.toCharArray()) {
                if (!VALID_BASES.contains(c)) {
                    return false;
                }
            }
        }

        // CONVERSIÓN A MATRIZ DE CARACTERES
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        //========================BÚSQUEDA DE SECUENCIAS======================

        int sequenceCount = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Horizontal: solo contar si es el inicio de una secuencia (no superpuesta)
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        // Solo contar si es el inicio: col == 0 o el carácter anterior es diferente
                        if (col == 0 || matrix[row][col - 1] != matrix[row][col]) {
                            sequenceCount++;
                            if (sequenceCount > 1) return true;  // Early termination
                        }
                    }
                }

                // Vertical: solo contar si es el inicio de una secuencia (no superpuesta)
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        // Solo contar si es el inicio: row == 0 o el carácter anterior es diferente
                        if (row == 0 || matrix[row - 1][col] != matrix[row][col]) {
                            sequenceCount++;
                            if (sequenceCount > 1) return true;
                        }
                    }
                }

                // Diagonal Descendente: solo contar si es el inicio de una secuencia (no superpuesta)
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDescending(matrix, row, col)) {
                        // Solo contar si es el inicio: row == 0 o col == 0 o el carácter diagonal anterior es diferente
                        if ((row == 0 || col == 0) || matrix[row - 1][col - 1] != matrix[row][col]) {
                            sequenceCount++;
                            if (sequenceCount > 1) return true;
                        }
                    }
                }

                // Diagonal Ascendente: solo contar si es el inicio de una secuencia (no superpuesta)
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAscending(matrix, row, col)) {
                        // Solo contar si es el inicio: row == n-1 o col == 0 o el carácter diagonal anterior es diferente
                        if ((row == n - 1 || col == 0) || matrix[row + 1][col - 1] != matrix[row][col]) {
                            sequenceCount++;
                            if (sequenceCount > 1) return true;
                        }
                    }
                }
            }
        }

        return false;  // Solo encontró 0 o 1 secuencia
    }

    //=====================================================MÉTODOS PARA VERIFICAR CADENAS DE 4 CARACTERES ===============================
    //Siempre misma fila, me desplazo por las columnas
    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row][col + 1] == base &&
                matrix[row][col + 2] == base &&
                matrix[row][col + 3] == base;
    }

    //Cambio de fila, siempre en la misma columna
    private boolean checkVertical(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
                matrix[row + 2][col] == base &&
                matrix[row + 3][col] == base;
    }

    //Me desplazo de fila y columna
    private boolean checkDiagonalDescending(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
                matrix[row + 2][col + 2] == base &&
                matrix[row + 3][col + 3] == base;
    }

    // Resto filas para subir, me desplazo a la derecha
    private boolean checkDiagonalAscending(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row - 1][col + 1] == base &&
                matrix[row - 2][col + 2] == base &&
                matrix[row - 3][col + 3] == base;
    }
}