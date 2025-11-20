package com.utn.DetectorDeMutantes.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return false;
    }
}

