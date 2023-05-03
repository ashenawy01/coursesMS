package com.sigma.coursesMS.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordPolicyValidator.class)
public @interface PasswordPolicy {
    String message() default "Insufficient password! " +
            "\nAt least 8 characters" +
            "\nIncludes number, Uppercase, lowercase and special character (ex: &,$,#)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
