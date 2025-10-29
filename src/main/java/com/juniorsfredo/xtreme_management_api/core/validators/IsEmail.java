package com.juniorsfredo.xtreme_management_api.core.validators;

import com.juniorsfredo.xtreme_management_api.infrastructure.validators.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsEmail {

    String message() default "Invalid Email, verify and try again";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
