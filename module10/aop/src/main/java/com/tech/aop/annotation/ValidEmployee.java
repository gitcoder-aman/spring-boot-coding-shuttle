package com.tech.aop.annotation;

import com.tech.aop.validation.EmployeeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmployeeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmployee {

    String message() default "Invalid Employee data";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
