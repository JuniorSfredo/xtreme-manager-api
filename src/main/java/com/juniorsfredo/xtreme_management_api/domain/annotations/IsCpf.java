package com.juniorsfredo.xtreme_management_api.domain.annotations;

import com.juniorsfredo.xtreme_management_api.infrastructure.config.validators.CpfValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CpfValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateCpf {

    String message() default "Invalid CPF, verify and try again";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

