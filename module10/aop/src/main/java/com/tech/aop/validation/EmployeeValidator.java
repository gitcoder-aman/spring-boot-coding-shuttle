package com.tech.aop.validation;

import com.tech.aop.annotation.ValidEmployee;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeValidator implements ConstraintValidator<ValidEmployee,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }

        // Example rule: EMP-123 format
        return s.matches("EMP-\\d{3}");
    }
}
