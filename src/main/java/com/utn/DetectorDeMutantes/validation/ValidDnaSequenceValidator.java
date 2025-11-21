package com.utn.DetectorDeMutantes.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {
    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');
    private static final int MIN_SIZE = 4;

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        // 1. Validar que dna no sea null o vacío
        if (dna == null || dna.length == 0) {
            return false;
        }

        final int n = dna.length;

        // 2. Validar tamaño mínimo 4x4
        if (n < MIN_SIZE) {
            return false;
        }

        // 3. Validar que sea matriz cuadrada NxN y que ninguna fila sea null
        for (String row : dna) {
            // Validar que ninguna fila sea null
            if (row == null) {
                return false;
            }
            
            // Validar que todas las filas tengan el mismo largo (matriz cuadrada)
            if (row.length() != n) {
                return false;
            }
        }

        // 4. Validar que solo contenga caracteres A, T, C, G (usando Set para O(1))
        for (String row : dna) {
            for (char c : row.toCharArray()) {
                if (!VALID_BASES.contains(c)) {
                    return false;
                }
            }
        }

        // Todas las validaciones pasaron
        return true;
    }
}

