package com.juniorsfredo.xtreme_management_api.infrastructure.config.validators;

import com.juniorsfredo.xtreme_management_api.domain.annotations.IsCpf;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<IsCpf, String> {

    private static final String CPF_PLAIN_REGEX = "^\\d{11}$";
    private static final String CPF_FORMATTED_REGEX = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        String cleanedValue = value.trim();

        return cleanedValue.matches(CPF_PLAIN_REGEX) || cleanedValue.matches(CPF_FORMATTED_REGEX);
    }
}
